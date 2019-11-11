package com.mygdx.zombiejump.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.mygdx.zombiejump.utils.Constants;

/**
 * BaseUI
 */
public class BaseUI {

    public static LabelStyle labelStyle; // BitmapFont + Color
    public static TextButtonStyle textButtonStyle; // NPD + BitmapFont + Color
    
    public static void loadAssets() {
        // parameters for generating a custom bitmap font
        FreeTypeFontGenerator fontGenerator = 
            new FreeTypeFontGenerator(Gdx.files.internal(Constants.UI_TFT_FONT));
        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
        fontParameters.size = 48;
        fontParameters.color = Color.WHITE;
        fontParameters.borderWidth = 2;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = TextureFilter.Linear;
        fontParameters.magFilter = TextureFilter.Linear;

        BitmapFont customFont = fontGenerator.generateFont(fontParameters);
        
        labelStyle = new LabelStyle();
        labelStyle.font = customFont;
        
        textButtonStyle = new TextButtonStyle();
        Texture   buttonTex   = new Texture( Gdx.files.internal(Constants.UI_BUTTON_TEXTURE) );
        NinePatch buttonPatch = new NinePatch(buttonTex, 24,24,24,24);
        textButtonStyle.up    = new NinePatchDrawable( buttonPatch );
        textButtonStyle.font      = customFont;
        textButtonStyle.fontColor = Color.GOLD;
    }
}