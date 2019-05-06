package com.kodilla.kodillafinalbackend.service;

import com.kodilla.kodillafinalbackend.domain.FlightSearchRequest;
import com.kodilla.kodillafinalbackend.domain.NotificationPreference;
import com.kodilla.kodillafinalbackend.domain.WeekendFlightOffer;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.SkyScannerFacade;
import com.kodilla.kodillafinalbackend.external.api.skyscanner.domain.Airport;
import com.kodilla.kodillafinalbackend.external.api.weather.WeatherClientFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

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
    private Set<String> getCitiesFromPreferences(List<NotificationPreference> allPreferences) {
        Set<String> cities = new HashSet<>();
        allPreferences.stream().forEach(e -> {
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
    private Map<String, List<Airport>> getAvailableAirportsForPrefferedCities(Set<String> cities) {
        Map<String, List<Airport>> citiesAndAirports = new HashMap<>();
        cities.stream().forEach(
                e -> citiesAndAirports.put(e, skyScannerFacade.getAirportsInCity(e))
        );
        return citiesAndAirports;
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
                                .departureAirport( departure.getAirportCode() )
                                .destinationAirport( destination.getAirportCode() )
                                .departureDay( getNextFriday() )
                                .build()
                );

                /**
                 * Swapping airports for return flight search request
                 */
                requestsForPreference.add(
                        FlightSearchRequest.builder()
                                .departureAirport( destination.getAirportCode() )
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
        Set<String> cities = getCitiesFromPreferences(allPreferences);
        Map<String, List<Airport>> citiesAndAirports = getAvailableAirportsForPrefferedCities(cities);

        allPreferences.stream().forEach(e -> {
            uniqueSearchRequestsForPreferenfces.addAll( getSearchRequestsForPreference(e, citiesAndAirports) );
        });

        return uniqueSearchRequestsForPreferenfces;
    }





    public void getOfferForPreference(NotificationPreference preference) {
        List<Airport> departureAirports = skyScannerFacade.getAirportsInCity( preference.getDepartureCity() );
        List<Airport> destinationAirports = skyScannerFacade.getAirportsInCity( preference.getDestinationCity() );

        //TODO: po chuj zbierać requesty searchy jeśli można od razu robić requesty????? Dać Tu od razu zbiory wyników
        Set<FlightSearchRequest> flightSearchRequestsThere = new HashSet<>();
        Set<FlightSearchRequest> flightSearchRequestsReturn = new HashSet<>();








    }

}
