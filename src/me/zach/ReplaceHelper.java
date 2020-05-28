package me.zach;

import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReplaceHelper {

    private List<RWith> list = new ArrayList<>();

    public ReplaceHelper(String... replaces) {
        if(replaces.length % 2 != 0) {
            System.out.println("ERROR WITH REPLACE HELPER!");
            return;
        }
        for(int i = 0; i < replaces.length; i+=2) {
            list.add(new RWith(replaces[i], replaces[i+1]));
        }
    }

    public String replace(String text) {
        String newText = text;
        for (RWith rwith : list) {
            if(text.contains(rwith.getReplacee())) {
                newText = newText.replace(rwith.getReplacee(), rwith.getReplaced());
            }
        }
        return newText;
    }



    class RWith {

        private String replacee;
        private String replaced;

        public RWith(String replacee, String replaced) {
            this.replacee = replacee;
            this.replaced = replaced;
        }

        public String getReplacee() {
            return replacee;
        }

        public String getReplaced() {
            return replaced;
        }
    }
}
