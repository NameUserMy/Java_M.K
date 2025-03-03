package com.backend.services.config;

import com.google.gson.JsonPrimitive;

public interface ConfigService {

  public JsonPrimitive  getValue(String path);
}
