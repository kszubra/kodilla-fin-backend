package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.FlightSearchRequest;
import com.kodilla.kodillafinalbackend.domain.NotificationPreference;
import com.kodilla.kodillafinalbackend.domain.FlightConnectionOfferWithWeather;
import com.kodilla.kodillafinalbackend.domain.TripOffer;
import com.kodilla.kodillafinalbackend.exceptions.UnableToGetWeatherForecastException;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.SkyScannerFacade;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.Airport;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.Flight;
import com.kodilla.kodillafinalbackend.external.api.weather.WeatherClientFacade;
import com.kodilla.kodillafinalbackend.external.api.weather.domain.DailyWeatherForecast;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Slf4j
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
        getAllCitiesFromPreferences( notificationPreferenceService.getAllPreferences() ).forEach(
                e -> averageTemperaturesForDestinationCities.put(e, getWeekendAverageTemperature(e))
        );

        return averageTemperaturesForDestinationCities;
    }

    private List<Flight> getConnectionsForRequest(FlightSearchRequest request) {
        List<Flight> result = skyScannerFacade.getFlightConnections(request.getDepartureAirport(), request.getDestinationAirport(), request.getDepartureDay());
        for(Flight flight : result) {
            flight.setDepartureCity( request.getDepartureCity() );
            flight.setDestinationCity( request.getDestinationCity() );
            flight.setDepartureAirport( request.getDepartureAirport() );
            flight.setDestinationAirport( request.getDestinationAirport() );
        }

        return result;
    }

    public List<Flight> getConnectionsForAllPreferences() {
        List<Flight> result = new ArrayList<>();
        Set<FlightSearchRequest> requests = this.getAllSearchRequests();
        log.info("Flight search requests number: " + requests.size());

        for(FlightSearchRequest request : requests) {
            log.info("Processing request: " + request);
            List<Flight> searchResult = this.getConnectionsForRequest(request);
            log.info("Found " + searchResult.size() + " flight(s) for the request");

            result.addAll( this.getConnectionsForRequest(request) );
            log.info("Total result size: " + result.size());
        }

        return result;
    }

    public List<FlightConnectionOfferWithWeather> getAllFlightOffersWithExpectedWeather() {
        List<FlightConnectionOfferWithWeather> result = new ArrayList<>();

        List<Flight> flights = this.getConnectionsForAllPreferences();
        Map<String, Double> weather = this.getWeatherForDestinationCities();

        for(Flight flight : flights) {
            BigDecimal temperature =  BigDecimal.valueOf(weather.get(flight.getDestinationCity())).setScale(2, RoundingMode.HALF_EVEN);
            result.add(
                    new FlightConnectionOfferWithWeather(flight,  temperature)
            );
        }

        return result;
    }

    /**
     * "Ultimate" method of the class, returning a complete set of connections matching preference requirements.
     *
     * @return
     */
    public Map<NotificationPreference, TripOffer> getPreferencesAndOffers() {
        log.info("Matching preferences with avaiable connections...");
        Map<NotificationPreference, TripOffer> preferencesAndOffers = new HashMap<>();
        List<NotificationPreference> preferences = notificationPreferenceService.getAllPreferences();
        List<FlightConnectionOfferWithWeather> offers = getAllFlightOffersWithExpectedWeather();

        for(NotificationPreference preference : preferences) {
            log.info("Processing preference: " + preference);
            Optional<FlightConnectionOfferWithWeather> cheapestThereFlight = offers.stream()
                    .filter(offer -> offer.getFlight().getDestinationCity().equals( preference.getDestinationCity().toLowerCase() )  )
                    .filter(offer -> offer.getFlight().getDepartureCity().equals( preference.getDepartureCity().toLowerCase() ))
                    .filter(offer -> offer.getExpectedTemperature().intValue() >= preference.getMinTemperature() )
                    .min( FlightConnectionOfferWithWeather::compareTo );

            log.info("Chepest there connection is: " + cheapestThereFlight);

            Optional<FlightConnectionOfferWithWeather> cheapestReturnFlight = offers.stream()
                    .filter(offer -> offer.getFlight().getDestinationCity().equals( preference.getDepartureCity().toLowerCase() )  )
                    .filter(offer -> offer.getFlight().getDepartureCity().equals( preference.getDestinationCity().toLowerCase() ))
                    .min( FlightConnectionOfferWithWeather::compareTo );
            log.info("Chepest return connection is: " + cheapestReturnFlight);

            if( cheapestReturnFlight.isPresent() && cheapestThereFlight.isPresent() ) {
                FlightConnectionOfferWithWeather thereFlight = cheapestThereFlight.get();
                FlightConnectionOfferWithWeather returnFlight = cheapestReturnFlight.get();
                BigDecimal totalPrice = thereFlight.getFlight().getMinPrice().add(returnFlight.getFlight().getMinPrice());

                /**
                 * If total cost of both flights (there and return) is lower or equal to max price declared in preference
                 * then create offer of those flights and add to the offer map
                 */
                if( totalPrice.compareTo( preference.getMaxPrice() ) < 1 ) {
                    log.info("Total price is acceptable, adding to offers");
                    TripOffer offer = new TripOffer(thereFlight, returnFlight, totalPrice);
                    preferencesAndOffers.put(preference, offer);
                }
            }
        }

        return preferencesAndOffers;
    }

}
