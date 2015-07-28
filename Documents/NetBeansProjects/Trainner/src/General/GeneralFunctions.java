/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package General;

import javafx.scene.input.KeyEvent;
import org.controlsfx.dialog.Dialogs;
import org.jetbrains.annotations.NotNull;

/**
 * @author Harvey
 */
public class GeneralFunctions {

    @NotNull
    public static String toSentenceCase(@NotNull String string) {
        //return a string that starts with an uppercase letter: sentence case that is.
        String[] temp;
        temp = string.toLowerCase().split("[ ]");
        String retrn = "";
        for (String next : temp) {
            retrn = retrn.concat(next.substring(0, 1).toUpperCase().
                    concat(next.substring(1))).concat(" ");
        }
        retrn = retrn.trim();
        return retrn;
    }

    public static void handleNumbers(@NotNull KeyEvent event) {
        char c = event.getCharacter().charAt(0);
        if (!((c >= '0') && (c <= '9') || (c == java.awt.event.KeyEvent.VK_TAB
                || c == java.awt.event.KeyEvent.VK_ENTER || c == java.awt.event.KeyEvent.VK_BACK_SPACE))) {
            Dialogs.create().title("Wrong Input").
                    message("You must enter only numbers here.").
                    showInformation();
            event.consume();
        }
    }

}
