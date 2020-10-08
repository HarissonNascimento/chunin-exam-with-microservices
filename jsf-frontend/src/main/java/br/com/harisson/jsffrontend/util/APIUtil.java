package br.com.harisson.jsffrontend.util;

public enum APIUtil {
    URL_VEHICLE_BASE("http://localhost:8082/gateway/spring-backend/v1/vehicle"),
    URL_VEHICLE_POST(concatUrlVehicleBaseWithComplement("/admin")),
    URL_VEHICLE_PUT(concatUrlVehicleBaseWithComplement("/admin")),
    URL_VEHICLE_DELETE_BY_ID(concatUrlVehicleBaseWithComplement("/admin/{id}")),
    URL_VEHICLE_LIST_ALL(concatUrlVehicleBaseWithComplement("/listAllVehicles")),
    URL_VEHICLE_LIST_IN_STOCK(concatUrlVehicleBaseWithComplement("/listVehiclesInStock")),
    URL_VEHICLE_LIST_SOLD(concatUrlVehicleBaseWithComplement("/listSoldVehicles")),
    URL_VEHICLE_LIST_BY_MODEL(concatUrlVehicleBaseWithComplement("/listVehiclesByModel")),
    URL_VEHICLE_FIND_BY_ID(concatUrlVehicleBaseWithComplement("/findVehicleById/{id}")),
    // ----------------------------------------------------------------------------------
    URL_BUYER_BASE("http://localhost:8082/gateway/spring-backend/v1/buyer"),
    URL_BUYER_POST(concatUrlBuyerBaseWithComplement("/admin")),
    URL_BUYER_PUT(concatUrlBuyerBaseWithComplement("/admin")),
    URL_BUYER_DELETE_BY_ID(concatUrlBuyerBaseWithComplement("/admin/{id}")),
    URL_BUYER_LIST_ALL(concatUrlBuyerBaseWithComplement("/listAllBuyers")),
    URL_BUYER_LIST_NON_CONTACTED(concatUrlBuyerBaseWithComplement("/listNonContactedBuyers")),
    URL_BUYER_LIST_CONTACTED(concatUrlBuyerBaseWithComplement("/listContactedBuyers")),
    URL_BUYER_FIND_BY_VEHICLE_ID(concatUrlBuyerBaseWithComplement("/findBuyersByVehicleId/{vehicleId}")),
    URL_BUYER_FIND_BY_ID(concatUrlBuyerBaseWithComplement("/findById/{id}")),
    // ----------------------------------------------------------------------------------
    URL_AUTH_LOGIN("http://localhost:8082/gateway/auth/login");

    private final String urlBase;

    APIUtil(String urlBase) {
        this.urlBase = urlBase;
    }

    private static String concatUrlVehicleBaseWithComplement(String complement) {
        return APIUtil.URL_VEHICLE_BASE.getUrl().concat(complement);
    }

    private static String concatUrlBuyerBaseWithComplement(String complement) {
        return APIUtil.URL_BUYER_BASE.getUrl().concat(complement);
    }

    public String getUrl() {
        return urlBase;
    }
}
