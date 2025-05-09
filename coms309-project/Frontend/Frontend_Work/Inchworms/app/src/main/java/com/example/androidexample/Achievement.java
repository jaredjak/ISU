package com.example.androidexample;

public class Achievement {
    private String name;
    private String description;
    private int tierSelected;
    private int state;
    private int count;
    private float progress;
    private boolean isTiered;
    private boolean isMoneyTier;
    private boolean isEquipped;

    public Achievement(String name, String description, int tierSelected, int state, int count, float progress,
                       boolean isTiered, boolean isMoneyTier, boolean isEquipped) {
        this.name = name;
        this.description = description;
        this.tierSelected = tierSelected;
        this.state = state;
        this.count = count;
        this.progress = progress;
        this.isTiered = isTiered;
        this.isMoneyTier = isMoneyTier;
        this.isEquipped = isEquipped;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getTierSelected() { return tierSelected; }
    public int getState() { return state; }
    public int getCount() { return count; }
    public float getProgress() { return progress; }
    public boolean isTiered() { return isTiered; }
    public boolean isMoneyTier() { return isMoneyTier; }
    public boolean isEquipped() { return isEquipped; }

    public void setEquipped(boolean equipped) {
        this.isEquipped = equipped;
    }

//    public void setTierSelected(int tier) {
//        tierSelected = tier;
//    }

}
