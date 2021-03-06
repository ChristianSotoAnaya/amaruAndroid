package com.example.a2106088.amaru.model;

import android.content.SharedPreferences;
import android.util.Log;


import com.example.a2106088.amaru.entity.Comment;
import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.entity.Pojo;
import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.services.NetworkService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 2107641 on 10/31/17.
 */



public class RetrofitNetwork implements Network
{
    SharedPreferences infousuario;



    private static final String BASE_URL = "https://amarucos.herokuapp.com/";

    private NetworkService networkService;

    private ExecutorService backgroundExecutor = Executors.newFixedThreadPool( 1 );

    public RetrofitNetwork()
    {
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).build();
        networkService = retrofit.create( NetworkService.class );
    }

    @Override
    public void login( final LoginWrapper loginWrapper, final RequestCallback<Token> requestCallback )
    {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<Token> call = networkService.login( loginWrapper );
                try
                {
                    Response<Token> execute = call.execute();
                    if (execute.code()==500){
                        NetworkException e= new NetworkException("usuario o constraseña inválidos");
                        requestCallback.onFailed(e);

                    }
                    else{
                        requestCallback.onSuccess( execute.body() );
                    }

                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );

    }

    @Override
    public void editdescription(final RequestCallback<User> requestCallback,final User user) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<User> call = networkService.editDescription(user);

                try
                {
                    Response<User> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void editemail(final RequestCallback<User> requestCallback,final User user) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<User> call = networkService.editEmail(user);

                try
                {
                    Response<User> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void editphone(final RequestCallback<User> requestCallback,final User user) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<User> call = networkService.editPhone(user);

                try
                {
                    Response<User> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void editimage(final RequestCallback<User> requestCallback, final User user) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<User> call = networkService.editImage(user);

                try
                {
                    Response<User> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void getuser(final RequestCallback<User> requestCallback, final String username) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<User> call = networkService.getuser(username);
                try
                {
                    Response<User> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void getUsers(final RequestCallback<List<User>> requestCallback) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<List<User>> call = networkService.getUsers();
                try
                {
                    Response<List<User>> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void getGrByName(final RequestCallback<List<Group>> requestCallback,final String name) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<List<Group>> call = networkService.getGrByName(name);
                try
                {
                    Response<List<Group>> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void editRate(final RequestCallback<User> requestCallback, final User user) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<User> call = networkService.editRate(user);

                try
                {
                    Response<User> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }


    @Override
    public void createUser(final RequestCallback<User> requestCallback, final User user) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<User> call = networkService.createUser(user);

                try
                {
                    Response<User> execute = call.execute();

                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void getGroupbyId(final RequestCallback<Group> requestCallback, final int groupname) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<Group> call = networkService.getGroupbyId(groupname);

                try
                {
                    Response<Group> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void getallgroups(final RequestCallback<List<Group>> requestCallback) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<List<Group>> call = networkService.getallgroups();

                try
                {
                    Response<List<Group>> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void createGroup(final RequestCallback<Group> requestCallback, final Group group) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<Group> call = networkService.createGroup(group);

                try
                {
                    Response<Group> execute = call.execute();

                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void getcategory(final RequestCallback<List<Group>> requestCallback, final String category) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<List<Group>> call = networkService.getCategory(category);

                try
                {
                    Response<List<Group>> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void editRateGroup(final RequestCallback<Group> requestCallback, final Group group) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<Group> call = networkService.editRateGroup(group);

                try
                {
                    Response<Group> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void buy(final RequestCallback<User> requestCallback, final User user) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<User> call = networkService.buy(user);

                try
                {
                    Response<User> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void subscribe(final RequestCallback<Boolean> requestCallback, final Pojo pojo) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<Boolean> call = networkService.subscribe(pojo);

                try
                {
                    Response<Boolean> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }

    @Override
    public void addCommentGroup(final RequestCallback<Group> requestCallback, final Comment comment) {
        backgroundExecutor.execute( new Runnable()
        {
            @Override
            public void run()
            {
                Call<Group> call = networkService.addCommentGroup(comment);

                try
                {
                    Response<Group> execute = call.execute();
                    requestCallback.onSuccess( execute.body() );
                }
                catch ( IOException e )
                {
                    requestCallback.onFailed( new NetworkException( null, e ) );
                }
            }
        } );
    }


    public void addSecureTokenInterceptor( final String token )
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor( new Interceptor()
        {
            @Override
            public okhttp3.Response intercept( Chain chain )
                    throws IOException
            {
                Request original = chain.request();

                Request request = original.newBuilder().header( "Accept", "application/json" ).header( "Authorization",
                        "Bearer "
                                + token ).method(
                        original.method(), original.body() ).build();
                return chain.proceed( request );
            }
        } );
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).client(
                        httpClient.build() ).build();
        networkService = retrofit.create( NetworkService.class );
    }

}