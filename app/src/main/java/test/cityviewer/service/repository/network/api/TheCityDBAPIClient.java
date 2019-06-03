package test.cityviewer.service.repository.network.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.NetworkBehavior;

import static test.cityviewer.Constants.CITY_ARRAY_LIST_CLASS_TYPE;
import static test.cityviewer.Constants.POPULAR_CITIES_BASE_URL;

public class TheCityDBAPIClient {

    private static boolean badNetworkImitationEnabled = false;

    public static CitiesAPIInterface getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        if (badNetworkImitationEnabled) {
            final NetworkBehavior behavior = NetworkBehavior.create();
            behavior.setDelay(1000, TimeUnit.MILLISECONDS);
            behavior.setFailurePercent(50);
            behavior.setVariancePercent(50);

            okBuilder = okBuilder
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            try {
                                Thread.sleep(behavior.calculateDelay(TimeUnit.MILLISECONDS));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (behavior.calculateIsFailure()) {
                                return new Response.Builder()
                                        .code(500)
                                        .message("MockError")
                                        .protocol(Protocol.HTTP_1_1)
                                        .request(chain.request())
                                        .body(ResponseBody.create(MediaType.parse("text/plain"), "MockError"))
                                        .build();
                            }
                            return chain.proceed(chain.request());
                        }
                    });
        }

        OkHttpClient client = okBuilder.build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CITY_ARRAY_LIST_CLASS_TYPE, new CitiesJsonDeserializer())
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(POPULAR_CITIES_BASE_URL);

        return builder.build().create(CitiesAPIInterface.class);
    }
}
