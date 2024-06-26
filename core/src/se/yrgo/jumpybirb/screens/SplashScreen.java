package se.yrgo.jumpybirb.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import se.yrgo.jumpybirb.JumpyBirb;
import se.yrgo.jumpybirb.utils.ScreenSwitcher;
import se.yrgo.jumpybirb.utils.Screens;

/***
 * The screen that shows when you start up the application.
 */
public final class SplashScreen implements Screen {
    private static final String TAG = SplashScreen.class.getSimpleName();
    private static final float FONT_SCALE = 4.0f;
    private Timer.Task timer;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture backgroundTexture;
    private static boolean playScreenDisplayed = false;
    private ScreenSwitcher screenSwitcher;

    /**
     * Constructor
     */
    public SplashScreen(ScreenSwitcher screenSwitcher) {
        this.screenSwitcher = screenSwitcher;
    }

    /***
     * This method is called when this screen becomes
     * the current screen for the game.
     */
    @Override
    public void show() {
        Gdx.app.log(TAG, "show() called");
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        font.getData().setScale(FONT_SCALE);

        // Load background image
        backgroundTexture = new Texture(Gdx.files.internal("Welcome1.jpg"));
        Gdx.app.log(TAG, "Image loaded successfully: " + backgroundTexture);

        // Set a timer to switch to the menu screen after 3 seconds
        setTimer();
        // Cancel if MenuScreen is displayed
        cancelTimer();
    }


    /***
     * This method is called when the Application should render itself.
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render background image
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.end();
    }

    private static void setTimer() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                // Switch to the menu screen
                if (!playScreenDisplayed) {
                    JumpyBirb.getScreenSwitcher().switchToScreen(Screens.MENU);
                }
            }
        }, 3); // 3 seconds
    }

    public void cancelTimer() {
        if (screenSwitcher.getCurrentScreen() == Screens.MENU) {
            timer.cancel();
        }
    }

    public static void setPlayScreenDisplayed(boolean playScreenDisplayed) {
        SplashScreen.playScreenDisplayed = playScreenDisplayed;
    }

    /***
     * This method is called when the Application is resized,
     * which can happen at any point during a non-paused state.
     * @param width the new width in pixels
     * @param height the new height in pixels
     */
    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize(" + width + ", " + height + ") called");
    }

    /***
     * This method is called when the Application is paused,
     * usually when it's not active or visible on-screen.
     */
    @Override
    public void pause() {
        Gdx.app.log(TAG, "pause() called");
    }

    /***
     * This method is called when the Application is resumed from
     * a paused state, usually when it regains focus.
     */
    @Override
    public void resume() {
        Gdx.app.log(TAG, "resume() called");
    }


    /***
     * This method is called when this screen is no longer
     * the current screen for the game.
     */
    @Override
    public void hide() {
        Gdx.app.log(TAG, "hide() called");
        dispose();
    }

    /***
     * This method is called when this screen should
     * release all resources. Preceded by a call to pause().
     */
    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose() called");
        batch.dispose();
        font.dispose();
        backgroundTexture.dispose();
    }
}