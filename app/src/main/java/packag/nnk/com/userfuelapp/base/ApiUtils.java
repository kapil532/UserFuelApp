package packag.nnk.com.userfuelapp.base;

import packag.nnk.com.userfuelapp.interfaces.ApiInterface;

public class ApiUtils
{
    public static ApiInterface getApiInterfaces()
    {
        return ApiClient.getApiClient(CommonClass.BASE_URL).create(ApiInterface.class);
    }


    public static ApiInterface getApiInterfacesForPetrolBunk()
    {
        return ApiClient.getApiClient(CommonClass.GET_PETROL_BUNK_DETAILS).create(ApiInterface.class);
    }
   /* public static ApiInterface getApiInterfaces1()
    {
        return ApiClient.getApiClient("http://aviation-edge.com").create(ApiInterface.class);
    }
    public static ApiInterface getApiInterfaces2()
    {
        return ApiClient.getApiClient("https://maps.google.com").create(ApiInterface.class);
    }*/
}
