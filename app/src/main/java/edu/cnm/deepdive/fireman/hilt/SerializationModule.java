package edu.cnm.deepdive.fireman.hilt;

import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import edu.cnm.deepdive.fireman.service.InstantTypeAdapter;
import java.time.Instant;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class SerializationModule {

  SerializationModule() {}

  @Provides
  @Singleton
  public Gson provideGson(@NonNull InstantTypeAdapter instantAdapter) {
    return new GsonBuilder()
        .registerTypeAdapter(Instant.class, instantAdapter)
        .excludeFieldsWithoutExposeAnnotation()
        .create();
  }

}
