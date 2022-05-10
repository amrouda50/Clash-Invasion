package com.mygdx.claninvasion.view.actors;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.claninvasion.model.Globals;

import java.util.ArrayList;
import java.util.List;

public class TableWithOptions extends Table {
    enum OptionState {
        MAIN,
        OPTION
    }

    private OptionState state;
    private final int width = 300;
    private final int heightForOption = 70;
    private final int x;
    private final int y;
    private List<Option> options;
    private final List<Option> parentOptions;
    private final Skin skin;
    private final Image back = new Image(Globals.BACK_TEXTURE);
    public Runnable onGoBack;

    public interface OptionActionable {
        void action(Option option);
    }

    public static class Option {
        private final String name;
        private final List<Option> childOptions;
        private Boolean multiple;
        private int price;
        private Option parentOption;
        private Skin skin;
        private ButtonWithIcon button;
        private BitmapFont font;
        private OptionActionable actionable;
        private final int index;

        public Option(String name, int price, Skin skin, BitmapFont font, int index) {
            this.name = name;
            this.price = price;
            this.multiple = false;
            childOptions = new ArrayList<>();
            this.parentOption = null;
            this.skin = skin;
            this.font = font;
            this.index = index;
            button = new ButtonWithIcon(skin,  name, font, price > 0 ? Globals.GOLD_TEXTURE :  null, Integer.toString(price));
        }

        public Option(String name, int price, Skin skin, BitmapFont font, List<Option> options, int index) {
            this(name, price, skin, font, index);
            for (Option option : options) {
                addChildOption(option);
            }
        }

        public void setActionable(OptionActionable actionable) {
            this.actionable = actionable;
            button.addClickListener(() -> actionable.action(this));
        }

        public void addChildOption(Option option) {
            if (this.childOptions.size() == 0) {
                this.multiple = true;
                this.price = 0;
            }
            option.parentOption = this;
            this.childOptions.add(option);
        }

        public int getPrice() {
            return price;
        }

        public Boolean getMultiple() {
            return multiple;
        }

        public List<Option> getChildOptions() {
            return childOptions;
        }

        public String getName() {
            return name;
        }

        public Option getParentOption() {
            return parentOption;
        }

        public void setParentOption(Option option) {
            this.parentOption = option;
        }

        public ButtonWithIcon getAction() {
            return button;
        }

        public int getIndex() {
            return index;
        }
    }

    public TableWithOptions(int x, int y, List<Option> options, Skin skin) {
        setVisible(false);
        setBackground(skin.getDrawable(Globals.ATLAS_WINDOW_BIG));
        state = OptionState.MAIN;
        this.x = x;
        this.y = y;
        this.options = options;
        this.skin = skin;
        initOptions();
        initBounds();
        initBackButton();
        parentOptions = new ArrayList<>();
        parentOptions.addAll(options);
        onGoBack = () -> {};
    }

    private void initBackButton() {
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goBack();
            }
        });
    }

    public void initBounds() {
        this.setBounds(x, y, width, options.size() * heightForOption + 20);
    }

    public void setIsOpen(boolean isOpen) {
        this.setVisible(isOpen);
    }

    public void setOnGoBack(Runnable runnable) {
        onGoBack = runnable;
    }

    private void clearOptions() {
        clearChildren();
        this.options.clear();
    }

    public void changeOptions(List<Option> options) {
        clearOptions();
        this.options = new ArrayList<>(options);
        initOptions();
    }

    public void goIntoChildOptions(int index) {
        Option selectedOption = this.options.get(index);
        this.state = OptionState.OPTION;
        changeOptions(selectedOption.getChildOptions());
        initBounds();
    }

    public void removeBackButton() {
        removeActor(back);
    }

    public void addBackButton() {
        add(back).padTop(-30).align(Align.right);
        row();
    }

    public void goBack() {
        if (this.state == OptionState.OPTION) {
            this.state = OptionState.MAIN;
            clearOptions();
            options.addAll(parentOptions);
            initOptions();
            initBounds();
            onGoBack.run();
        }
    }

    private void initOptions() {
        if (this.state == OptionState.OPTION) {
            addBackButton();
        }
        for (Option option : options) {
            add(option.getAction());
            row();
        }
    }
}
