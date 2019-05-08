package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.FlightSearchRequest;
import com.kodilla.kodillafinalbackend.domain.NotificationPreference;
import com.kodilla.kodillafinalbackend.domain.WeekendFlightOffer;
import com.kodilla.kodillafinalbackend.exceptions.UnableToGetWeatherForecastException;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.SkyScannerFacade;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.Airport;
import com.kodilla.kodillafinalbackend.external.api.weather.WeatherClientFacade;
import com.kodilla.kodillafinalbackend.external.api.weather.domain.DailyWeatherForecast;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WeekendFlightOffersCreator {
    private final NotificationPreferenceService notificationPreferenceService;
    private final SkyScannerFacade skyScannerFacade;
    private final WeatherClientFacade weatherClientFacade;

    private LocalDate getNextFriday() {
        return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
    }
    private LocalDate getSundayAfterDeparture() { return getNextFriday().plusDays(2); }



    /**
     * Method creates a set of all cities appearing in customers' preferences. To ensure equality of strings and
     * avoid duplicating cities, every city name gets changed to lower case
     *
     * @param allPreferences a list of customers' preferences to work on
     * @return
     */
    private Set<String> getAllCitiesFromPreferences(List<NotificationPreference> allPreferences) {
        Set<String> cities = new HashSet<>();
        allPreferences.forEach(e -> {
                        cities.add( e.getDepartureCity().toLowerCase() );
                        cities.add( e.getDestinationCity().toLowerCase() );
        });
        return cities;
    }

    /**
     * Having set of cities appearing in customers' preferences method checks via API airports available for every city
     * and maps with cities. Key is the name of the city and value is a list of airports available in the city.
     *
     * @param cities
     * @return
     */
    private Map<String, List<Airport>> getAvailableAirportsForPreferredCities(Set<String> cities) {
        Map<String, List<Airport>> citiesAndAirports = new HashMap<>();
        cities.forEach(
                e -> citiesAndAirports.put(e, skyScannerFacade.getAirportsInCity(e))
        );
        return citiesAndAirports;
    }

    /**
     * Creates a reversed map where Key value is airport code and value is the name of there city where airport is.
     *
     * @param citiesAndAirports
     * @return
     */
    private Map<String, String> createReverseMappingForCitiesAndAirports(Map<String, List<Airport>> citiesAndAirports) {
        Map<String, String> result = new HashMap<>();
        citiesAndAirports.entrySet().stream()
                .forEach(entry -> {
                        for(Airport airport : entry.getValue()) {
                            result.put(airport.getAirportCode(), entry.getKey());
                        }
                });

        return result;
    }

    /**
     * Knowing airports available in every city method takes customer's preference as @param and creates a set of possible
     * connections for both going there and returning.
     *
     * @param preference
     * @param citiesAndAirports
     * @return
     */
    private Set<FlightSearchRequest> getSearchRequestsForPreference (NotificationPreference preference, Map<String, List<Airport>> citiesAndAirports) {
        Set<FlightSearchRequest> requestsForPreference = new HashSet<>();
        List<Airport> departureAirports = citiesAndAirports.get( preference.getDepartureCity().toLowerCase() );
        List<Airport> destinationAirports = citiesAndAirports.get( preference.getDestinationCity().toLowerCase() );
        Map<String, String> reversedCitiesAndAirports = createReverseMappingForCitiesAndAirports(citiesAndAirports);

        /**
         * For every available in the city airport create a pair with every destination airport to create search request.
         * Created request adds to the set and creates a return request with swapped airports.
         */
        for(Airport departure : departureAirports) {
            for(Airport destination : destinationAirports) {

                /**
                 * Flight search when going there
                 */
                requestsForPreference.add(
                        FlightSearchRequest.builder()
                                .departureCity( reversedCitiesAndAirports.get( departure.getAirportCode() ) )
                                .departureAirport( departure.getAirportCode() )
                                .destinationCity( reversedCitiesAndAirports.get( destination.getAirportCode() ) )
                                .destinationAirport( destination.getAirportCode() )
                                .departureDay( getNextFriday() )
                                .build()
                );

                /**
                 * Swapping airports for return flight search request
                 */
                requestsForPreference.add(
                        FlightSearchRequest.builder()
                                .departureCity( reversedCitiesAndAirports.get( destination.getAirportCode() )  )
                                .departureAirport( destination.getAirportCode() )
                                .destinationCity( reversedCitiesAndAirports.get( departure.getAirportCode() ) )
                                .destinationAirport( departure.getAirportCode() )
                                .departureDay( getSundayAfterDeparture() )
                                .build()
                );
            }
        }

        return requestsForPreference;
    }

    public Set<FlightSearchRequest> getAllSearchRequests() {
        Set<FlightSearchRequest> uniqueSearchRequestsForPreferenfces = new HashSet<>();

        List<NotificationPreference> allPreferences = notificationPreferenceService.getAllPreferences();
        Set<String> cities = getAllCitiesFromPreferences(allPreferences);
        Map<String, List<Airport>> citiesAndAirports = getAvailableAirportsForPreferredCities(cities);

        allPreferences.forEach(e -> {
            uniqueSearchRequestsForPreferenfces.addAll( getSearchRequestsForPreference(e, citiesAndAirports) );
        });

        return uniqueSearchRequestsForPreferenfces;
    }

    /**
     * Method creates a set of destination cities appearing in customers' preferences. To ensure equality of strings and
     * avoid duplicating cities, every city name gets changed to lower case
     *
     * @return
     */
    private Set<String> getDestinationCitiesFromPreferences() {
        HashSet<String> destinationCities = new HashSet<>();
        notificationPreferenceService.getAllPreferences().forEach(
                e -> destinationCities.add( e.getDestinationCity().toLowerCase() )
        );

        return destinationCities;
    }

    /**
     * Tells whether provided as @param LocalDate belongs to the upcoming weekend, meaning:
     * from departure day ("after day before") to sunday
     *
     * @param date
     * @return
     */
    private boolean isNextWeekendDay(LocalDate date) {
        return (date.isAfter(getNextFriday().minusDays(1)) && date.isBefore(getSundayAfterDeparture().plusDays(1)));
    }

    private Double getWeekendAverageTemperature(String city) {
        return weatherClientFacade.getWeatherForecast(city).getDailyForecasts().stream()
                .filter(e -> isNextWeekendDay( e.getDate() ))
                .mapToDouble(DailyWeatherForecast::getMaxTemperature )
                .average().orElseThrow(UnableToGetWeatherForecastException::new);
    }

    /**
     * Counts average temperature for destination cities during upcoming weekend. Returns it as a Map where Key is
     * city name and value is the expected average temperature
     *
     * @return
     */
    public Map<String, Double> getWeatherForDestinationCities() {
        Map<String, Double> averageTemperaturesForDestinationCities = new HashMap<>();
        getDestinationCitiesFromPreferences().forEach(
                e -> averageTemperaturesForDestinationCities.put(e, getWeekendAverageTemperature(e))
        );

        return averageTemperaturesForDestinationCities;
    }

    public void getOfferForPreference(NotificationPreference preference) {
        WeekendFlightOffer offer =  new WeekendFlightOffer();
        String goingThereDestinationCity = preference.getDestinationCity();
        String homeCity = preference.getDepartureCity();

        //TODO: po chuj zbierać requesty searchy jeśli można od razu robić requesty????? Dać Tu od razu zbiory wyników
        Set<FlightSearchRequest> flightSearchRequestsThere = new HashSet<>();
        Set<FlightSearchRequest> flightSearchRequestsReturn = new HashSet<>();

    }

}
