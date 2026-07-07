package de.hbrs.se2.womm.views.extra;

import com.vaadin.flow.component.html.Image;

public class ASSETS {

    public static final String STYLEGUIDE = "themes/style_guide/StyleSheetVaadin.png";

    public static Image buildPlaceholder(int width, int height) {
        Image placeholderIMG = new Image(ASSETS.IMG.PLACEHOLDER, "placeholder");
        placeholderIMG.setWidth(width + "px");
        placeholderIMG.setHeight(height + "px");
        return placeholderIMG;
    }
    public static Image buildPlaceholder(int height) {
        Image placeholderIMG = new Image(ASSETS.IMG.PLACEHOLDER, "placeholder");
        placeholderIMG.setHeight(height + "px");
        return placeholderIMG;
    }

    static public class StyleGuide {
        public static final String LogoText = "themes/style_guide/LogoText.png"; //ToDo
        public static final String LogoPerson = "themes/style_guide/LogoPerson.png"; //ToDo
        public static final String LogoWork = "themes/style_guide/LogoWork.png"; //ToDo
    }

    static public class LOGO {

    }

    static public class IMG {

        public static String IMG1 = "themes/theme_1/Hiring_pic.jpg";
        public static String IMG2 = "themes/theme_1/Womm_big_logo.png";
        public static String IMG3 = "themes/theme_1/Womm_text_logo.png";
        public static String IMG4 = "themes/theme_1/banner.jpg";
        public static String IMG5 = "themes/theme_1/banner_new.jpg";
        public static String IMG6 = "themes/theme_1/logo.png";
        public static String IMG7 = "themes/theme_1/previewPage.png";
        public static String IMG8 = "themes/theme_1/user.png";
        public static String IMG9 = "themes/theme_1/logo_placeholder.png";
        public static String PLACEHOLDER = "themes/theme_1/logo_placeholder.png";


    }

    static public class ICONS {
    }

    static public class BANNER {
        public static String BANNER = "themes/banner/banner.jpg";
        public static String BANNER2 = "themes/banner/banner2.jpg";
        public static String BANNER3 = "themes/banner/banner3.jpg";
    }

    static public class RANDOM {
        public static String USER = "themes/theme_1/user.png";

    }
}
