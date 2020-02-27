package com.jojoreference.mtgmod.card;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jojoreference.mtgmod.MTGMod;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SetData {

    private static JsonArray ALLCARDS;
    private static JsonObject ALLSETS;
    private Random rand;

    public SetData() throws IOException {
        InputStream input = MTGMod.class.getClassLoader().getResourceAsStream("carddata/AllCards.json");
        JsonParser parser = new JsonParser();
        if(input != null)
            ALLCARDS = (JsonArray) parser.parse(new InputStreamReader(input, StandardCharsets.UTF_8));
        input = MTGMod.class.getClassLoader().getResourceAsStream("carddata/TrimmedAllSets.json");
        if(input != null)
            ALLSETS = (JsonObject) parser.parse(new InputStreamReader(input, StandardCharsets.UTF_8));
        input.close();
        rand = new Random();
    }

    public JsonObject RandomCard() {
        Random rand = new Random();
        return (JsonObject) ALLCARDS.get(rand.nextInt(ALLCARDS.size()));
    }

    public JsonObject RandomCardFromSet(String set) {
        JsonArray cardPool = (JsonArray) ((JsonObject) ALLSETS.get(set)).get("cards");
        return (JsonObject) cardPool.get(rand.nextInt(cardPool.size()));
    }

    //TODO
    public List<JsonObject> GenerateBooster(String set) {
        JsonArray boosterV3 = (JsonArray) ((JsonObject) ALLSETS.get(set)).get("boosterV3");
        List<JsonObject> pack = new ArrayList<>();
        for(JsonElement o : boosterV3) {
            if(o.isJsonArray()) {
                if(rand.nextInt(6) == 0)
                    pack.add(BoosterCard(set, "mythic rare"));
                else
                    pack.add(BoosterCard(set, "rare"));
            } else {
                pack.add(BoosterCard(set, o.getAsString()));
            }
        }
        return pack;
    }

    public JsonObject BoosterCard(String set, String rarity) {
        //if(((List<String>)(((JsonObject)ALLSETS.get(set)).get("boosterV3"))).contains(rarity)) {
        JsonArray cardPool = (JsonArray) ((JsonObject) ALLSETS.get(set)).get("cards");
        JsonObject randCard = (JsonObject) cardPool.get(rand.nextInt(cardPool.size()));
        while(!randCard.get("rarity").getAsString().equals(rarity))
            randCard = (JsonObject) cardPool.get(rand.nextInt(cardPool.size()));
        return randCard;
    }
}
