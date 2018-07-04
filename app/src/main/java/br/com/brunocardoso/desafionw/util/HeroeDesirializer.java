package br.com.brunocardoso.desafionw.util;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import br.com.brunocardoso.desafionw.model.Hero;

public class HeroeDesirializer implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement root = json.getAsJsonObject();

//        if ( root.getAsJsonObject().get("class_id") != null){
//            int class_id = root.getAsJsonObject().get("class_id").getAsInt();
//            String class_name = root.getAsJsonObject().get("class_name").getAsString();
//
//            classe.setId( class_id );
//            classe.setName( class_name );

//            root.getAsJsonArray().add( new Gson().toJsonTree( classe, Classe.class ));
//        }

        return (new Gson().fromJson(root, Hero.class));
    }
}
