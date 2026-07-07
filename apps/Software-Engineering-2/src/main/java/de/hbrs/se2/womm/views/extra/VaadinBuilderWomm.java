package de.hbrs.se2.womm.views.extra;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class VaadinBuilderWomm {
    private static boolean devMode = false;
    private static boolean translateText = true;
    private static Map<String, String> translateTextMap = TranslateMap.translateMap;
    private static Set<String> newTextNeedsToBeTranslated = new HashSet<>();
    public ButtonBuilder Button = new ButtonBuilder();
    public TextFieldBuilder TextField = new TextFieldBuilder();
    public H1Builder H1 = new H1Builder();
    public H2Builder H2 = new H2Builder();
//    public H3Builder H3 = new H3Builder();
    public H4Builder H4 = new H4Builder();
    public TextBuilder Text = new TextBuilder();
    public SpanBuilder Span = new SpanBuilder();
    public ParagraphBuilder Paragraph = new ParagraphBuilder();
    public EmailFieldBuilder EmailField = new EmailFieldBuilder();
    public PasswordFieldBuilder PasswordField = new PasswordFieldBuilder();
    public DatePickerBuilder DatePicker = new DatePickerBuilder();


    public String translateText(String text) {
        if (text == null) return null;
        if (!translateText) return text;
        return addTextToFrontEndCheck(text);
    }
    public static void toggleTranslateText() {
        translateText = !translateText;
    }

    public static void toggleDevMode() {
        devMode = !devMode;
    }

    public class H1Builder {
        private int countH1 = 0;

        public H1 create(String text) {
            String translatedText = translateText(text);
            countH1++;
            String querryId = "h1-builder-" + this.countH1;
            if (devMode) translatedText = querryId;
            H1 h1 = new H1(translatedText);
            h1.setText(translatedText);

            h1.setId(querryId);
            return h1;
        }
    } public class DatePickerBuilder {
        private int countDatePicker = 0;

        public DatePicker create(String text) {
            String translatedText = translateText(text);
            countDatePicker++;
            String querryId = "datepicker-builder-" + this.countDatePicker;
            if (devMode) translatedText = querryId;
            DatePicker datepicker = new DatePicker(translatedText);
//            datepicker.setText(translatedText);

            datepicker.setId(querryId);
            return datepicker;
        }
    }

    public class H2Builder {
        private int countH2 = 0;

        public H2 create(String text) {
            String translatedText = translateText(text);
            countH2++;
            String querryId = "h2-builder-" + this.countH2;
            if (devMode) translatedText = querryId;
            H2 h2 = new H2(translatedText);
            h2.setText(translatedText);
            h2.setId(querryId);
            return h2;
        }
    } public class H3Builder {
        private int countH3 = 0;
        public H3 create(String text) {
            String translatedText = translateText(text);
            countH3++;
            String querryId = "h3-builder-" + this.countH3;
            if (devMode) translatedText = querryId;
            H3 h3 = new H3(translatedText);
            h3.setText(translatedText);
            h3.setId(querryId);
            return h3;
        }
    }

    public class H4Builder {
        private int countH4 = 0;

        public H4 create(String text) {
            String translatedText = translateText(text);
            countH4++;
            String querryId = "h4-builder-" + this.countH4;
            if (devMode) translatedText = querryId;
            H4 h4 = new H4(translatedText);
            h4.setText(translatedText);
            h4.setId(querryId);
            return h4;
        }
    }

    public class ParagraphBuilder {
        private int countParagraph = 0;

        public Paragraph create(String text) {
            String translatedText = translateText(text);
            countParagraph++;
            String querryId = "paragraph-builder-" + this.countParagraph;
            if (devMode) translatedText = querryId;
            Paragraph paragraph = new Paragraph(translatedText);
            paragraph.setText(translatedText);
            paragraph.setId(querryId);
            return paragraph;
        }
    }

    public class EmailFieldBuilder {
        private int countEmailField = 0;

        public EmailField create(String text) {
            String translatedText = translateText(text);
            countEmailField++;
            String querryId = "emailfield-builder-" + this.countEmailField;
            if (devMode) translatedText = querryId;
            EmailField emailfield = new EmailField(translatedText);
//            emailfield.setText(translatedText);
            emailfield.setId(querryId);
            return emailfield;
        }
    }

    public class PasswordFieldBuilder {
        private int countPasswordField = 0;

        public PasswordField create(String text) {
            String translatedText = translateText(text);
            countPasswordField++;
            String querryId = "passwordField-builder-" + this.countPasswordField;
            if (devMode) translatedText = querryId;
            PasswordField passwordField = new PasswordField(translatedText);
//            emailfield.setText(translatedText);
            passwordField.setId(querryId);
            return passwordField;
        }
    }

    public class SpanBuilder {
        private int countParagraph = 0;

        public Span create(String text) {
            String translatedText = translateText(text);
            countParagraph++;
            String querryId = "span-builder-" + this.countParagraph;
            if (devMode) translatedText = querryId;
            Span span = new Span(translatedText);
            span.setText(translatedText);
            span.setId(querryId);
            return span;
        }
    }


    public class TextBuilder {
        private int countTextBuilder = 0;

        public Text create(String text) {
            String translatedText = translateText(text);
            countTextBuilder++;
            String querryId = "text-builder-" + this.countTextBuilder;
            Text textText = new Text(translatedText);
            textText.setText(translatedText);
            return textText;
        }
    }

    public class ButtonBuilder {
        private int countButtonOnView = 0;

        public Button create(String text) {
            String translatedText = translateText(text);
            this.countButtonOnView++;
            String querryId = "button-builder-" + this.countButtonOnView;
            if (devMode) translatedText = querryId;
            Button button = new Button(translatedText);
            button.setText(translatedText);
            button.setId(querryId);
            return button;
        }

        public Button create(String text, Icon icon) {
            Button button = create(text);
            button.setIcon(icon);
            return button;
        }
        public Button create(String text,  ComponentEventListener<ClickEvent<Button>> clickListener) {
            Button button = create(text);
            button.addClickListener(clickListener);
            return button;
        }

    }

    public class TextFieldBuilder {
        private int countTextFieldOnView = 0;

        public TextField create(String textAbove) {
            return create(textAbove, "");
        }

        public TextField create(String textAbove, String textValuePlaceholder) {
            String translatedTextAbove = translateText(textAbove);
            String translatedTextTextValuePlaceholder = translateText(textValuePlaceholder);

            this.countTextFieldOnView++;
            String querryId = "text-field-builder-" + this.countTextFieldOnView;
            if (devMode) translatedTextTextValuePlaceholder = querryId;

            TextField textField = new TextField(translatedTextAbove);
            if (textValuePlaceholder.isEmpty()) textField.setValue(translatedTextTextValuePlaceholder);
            textField.setId(querryId);
            return textField;
        }
    }

    public static String translateTextStatic(String text) {
        if (text == null) return null;
        if (!translateText) return text;
        return addTextToFrontEndCheck(text);
    }

    private static String addTextToFrontEndCheck(String text) {
        boolean alreadyContainsText = (translateTextMap.containsKey(text));
        if (alreadyContainsText) {
            return translateTextMap.get(text);
        } else {
            newTextNeedsToBeTranslated.add(text);
            System.out.println("put(\"" + text + "\", \"" + text + "<-\");");
            translateTextMap.put(text, "->" + text);
            return text;
        }
    }

    public static void printAllTextNotTranslatedToConsole() {
        if (newTextNeedsToBeTranslated.isEmpty()) {
            System.out.println("Nothing to translate");
            System.out.println("Translation can be found in TranslateMap.java");
            return;
        }
        System.out.println("Not translated:");
        System.out.println(newTextNeedsToBeTranslated);
        System.out.println("------------");
        System.out.println("Add this to TranslateMap.java");
        for (String translateMe : newTextNeedsToBeTranslated) {
            System.out.println("put(\"" + translateMe + "\", \"\");");
        }
    }

}
