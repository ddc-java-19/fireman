package edu.cnm.deepdive.fireman.service;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.Instant;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InstantTypeAdapter extends TypeAdapter<Instant> {

  @Inject
  InstantTypeAdapter() {}

  @Override
  public void write(JsonWriter jsonWriter, Instant instant) throws IOException {
    jsonWriter.value(instant.toString());
  }

  @Override
  public Instant read(JsonReader jsonReader) throws IOException {
    return Instant.parse(jsonReader.nextString());
  }

}