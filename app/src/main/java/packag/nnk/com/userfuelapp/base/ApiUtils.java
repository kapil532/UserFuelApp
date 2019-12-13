package packag.nnk.com.userfuelapp.base;

import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.interfaces.IRetrofit;

public class ApiUtils
{
    public  ApiInterface getApiInterfaces()
    {
        return  new ApiClient().getApiClient(CommonClass.BASE_URL).create(ApiInterface.class);
    }


    public  ApiInterface getApiInterfacesForPetrolBunk()
    {
        return  new ApiClient().getApiClient(CommonClass.GET_PETROL_BUNK_DETAILS).create(ApiInterface.class);
    }


    public  IRetrofit getApiInterfacesForSlack()
    {
        return new ApiClient().getApiClient(CommonClass.POST_SUPPORT_SLACK).create(IRetrofit.class);
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
