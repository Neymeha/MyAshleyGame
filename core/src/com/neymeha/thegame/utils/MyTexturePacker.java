package com.neymeha.thegame.utils;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class MyTexturePacker {

    public static void main(String[] args) throws Exception {
        String inputFolder = "assets/_texturePacker/input/game";
        String outputFolder = "assets/_texturePacker/output";
        String packedFileName = "game";
        TexturePacker.process(inputFolder,outputFolder,packedFileName);
        inputFolder = "assets/_texturePacker/input/loading";
        outputFolder = "assets/_texturePacker/output";
        packedFileName = "loading";
        TexturePacker.process(inputFolder,outputFolder,packedFileName);
    }
}
