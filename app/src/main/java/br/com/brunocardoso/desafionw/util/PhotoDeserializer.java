package br.com.brunocardoso.desafionw.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import br.com.brunocardoso.desafionw.model.Classe;
import br.com.brunocardoso.desafionw.model.Photo;

public class PhotoDeserializer implements JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement photo = json.getAsJsonObject();

        if ( photo.getAsJsonObject().get("photos") != null){
            photo = json.getAsJsonObject().get("photos");
        }

        return (new Gson().fromJson(photo, Photo.class));
    }
}
