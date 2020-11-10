package com.wecare.android.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ResponseTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> jsonElementAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegateAdapter.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                // Ignore extraneous data and read in only the response data when the response is a success
                JsonElement jsonElement = jsonElementAdapter.read(in);
                if (jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has(ServerKeys.SUCCESS)) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    if (jsonObject.get(ServerKeys.SUCCESS).toString().equals("true")) {
                        if (jsonObject.has(ServerKeys.DATA) && (jsonObject.get(ServerKeys.DATA).isJsonObject() || jsonObject.get(ServerKeys.DATA).isJsonArray()) &&
                                !jsonObject.get(ServerKeys.DATA).toString().equals("{}")) {
                            jsonElement = jsonObject.get(ServerKeys.DATA);
                        } else {
                            // primitive JSON
//                            jsonElement = jsonObject.getAsJsonPrimitive(ServerKeys.DATA);
                            return delegateAdapter.fromJsonTree(jsonElement);
                        }
                    } else {///this handling for social login only because of 422 error code with data inside
                        if (jsonObject.get(ServerKeys.SUCCESS).toString().equals("false") &&
                                jsonObject.has(ServerKeys.DATA) && (jsonObject.get(ServerKeys.DATA).isJsonObject()) &&
                                jsonObject.has(ServerKeys.ERRORS) && (jsonObject.get(ServerKeys.ERRORS).isJsonObject())
                                && (jsonObject.get(ServerKeys.ERRORS).getAsJsonObject().get(ServerKeys.ERROR_CODE).getAsInt()) == 422) {
                            jsonElement = jsonObject.get(ServerKeys.DATA);
                        }
                    }
                    //else {
//                        String messages = "";
//                        String errorCode = "";
//                        try {
//                            JSONObject responseJsonObject = new JSONObject(jsonElement.toString());
//                            if (responseJsonObject.has(ServerKeys.ERRORS)) {
//                                responseJsonObject = responseJsonObject.getJSONObject(ServerKeys.ERRORS);
//                                errorCode = responseJsonObject.getString(ServerKeys.ERROR_CODE);
//
//                                JSONArray jsonArray = responseJsonObject.getJSONArray(ServerKeys.MESSAGE);
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject jsonErrorObject = jsonArray.getJSONObject(i);
//                                    messages += jsonErrorObject.getString(ServerKeys.MESSAGE) + "\n";
//                                }
//                            }
//                        } catch (JsonIOException | NullPointerException | JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
                return delegateAdapter.fromJsonTree(jsonElement);
            }
        }.nullSafe();
    }

}
