package com.github.technus.tectech.thing.metaTileEntity.multi;

import static com.github.technus.tectech.TecTech.eyeOfHarmonyRecipeStorage;
import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.textureOffset;
import static com.github.technus.tectech.thing.casing.GT_Block_CasingsTT.texturePage;
import static com.github.technus.tectech.thing.casing.TT_Container_Casings.eyeOfHarmonyRenderBlock;
import static com.github.technus.tectech.thing.casing.TT_Container_Casings.sBlockCasingsBA0;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.*;
import static gregtech.api.enums.GT_Values.AuthorColen;
import static gregtech.api.util.GT_StructureUtility.ofHatchAdderOptional;
import static gregtech.api.util.GT_Utility.formatNumbers;
import static java.lang.Math.*;
import static net.minecraft.util.EnumChatFormatting.*;

import appeng.util.ReadableNumberConverter;
import com.github.technus.tectech.recipe.EyeOfHarmonyRecipe;
import com.github.technus.tectech.recipe.EyeOfHarmonyRecipeStorage;
import com.github.technus.tectech.thing.block.TileEyeOfHarmony;
import com.github.technus.tectech.thing.casing.TT_Block_SpacetimeCompressionFieldGenerators;
import com.github.technus.tectech.thing.casing.TT_Block_StabilisationFieldGenerators;
import com.github.technus.tectech.thing.casing.TT_Block_TimeAccelerationFieldGenerators;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.render.TT_RenderedExtendedFacingTexture;
import com.github.technus.tectech.util.CommonValues;
import com.github.technus.tectech.util.ItemStackLong;
import com.google.common.collect.ImmutableList;
import com.gtnewhorizon.structurelib.alignment.constructable.IConstructable;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IGlobalWirelessEnergy;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_OutputBus;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gregtech.common.tileentities.machines.GT_MetaTileEntity_Hatch_OutputBus_ME;
import gregtech.common.tileentities.machines.GT_MetaTileEntity_Hatch_Output_ME;
import java.awt.*;
import java.util.*;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.libraries.com.google.common.math.LongMath;

@SuppressWarnings("SpellCheckingInspection")
public class GT_MetaTileEntity_EM_EyeOfHarmony extends GT_MetaTileEntity_MultiblockBase_EM
        implements IConstructable, IGlobalWirelessEnergy {
    // Region variables.
    private static Textures.BlockIcons.CustomIcon ScreenOFF;
    private static Textures.BlockIcons.CustomIcon ScreenON;

    private int spacetimeCompressionFieldMetadata = -1;
    private int timeAccelerationFieldMetadata = -1;
    private int stabilisationFieldMetadata = -1;

    private static final double spacetimeCasingDifferenceDiscountPercentage = 0.03;

    private String userUUID = "";
    private String userName = "";
    private long euOutput = 0;

    private final Stack<Long> computationStack = new Stack<>();

    // Multiblock structure.
    private static final IStructureDefinition<GT_MetaTileEntity_EM_EyeOfHarmony> STRUCTURE_DEFINITION =
            IStructureDefinition.<GT_MetaTileEntity_EM_EyeOfHarmony>builder()
                    .addShape("main", transpose(new String[][] {
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "            CCCCCCCCC            ",
                            "               C C               ",
                            "            CCCCCCCCC            ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "              DDDDD              ",
                            "             DDCDCDD             ",
                            "         CCCCDCCDCCDCCCC         ",
                            "             DDDDDDD             ",
                            "         CCCCDCCDCCDCCCC         ",
                            "             DDCDCDD             ",
                            "              DDDDD              ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "                D                ",
                            "                D                ",
                            "             DDDDDDD             ",
                            "            DD     DD            ",
                            "            D  EEE  D            ",
                            "       CCC  D EAAAE D  CCC       ",
                            "          DDD EAAAE DDD          ",
                            "       CCC  D EAAAE D  CCC       ",
                            "            D  EEE  D            ",
                            "            DD     DD            ",
                            "             DDDDDDD             ",
                            "                D                ",
                            "                D                ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "                D                ",
                            "                D                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "      CC                 CC      ",
                            "        DD             DD        ",
                            "      CC                 CC      ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                D                ",
                            "                D                ",
                            "               C C               ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "              CCCCC              ",
                            "                D                ",
                            "                A                ",
                            "                A                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "      C                   C      ",
                            "     CC                   CC     ",
                            "      CDAA             AADC      ",
                            "     CC                   CC     ",
                            "      C                   C      ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                A                ",
                            "                A                ",
                            "                D                ",
                            "              CCCCC              ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "                D                ",
                            "             SEEAEES             ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "       S                 S       ",
                            "       E                 E       ",
                            "    CC E                 E CC    ",
                            "      DA                 AD      ",
                            "    CC E                 E CC    ",
                            "       E                 E       ",
                            "       S                 S       ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "             SEEAEES             ",
                            "                D                ",
                            "               C C               ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "              CCCCC              ",
                            "                D                ",
                            "                A                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "    C                       C    ",
                            "   CC                       CC   ",
                            "    CDA                   ADC    ",
                            "   CC                       CC   ",
                            "    C                       C    ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                A                ",
                            "                D                ",
                            "              CCCCC              ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "                D                ",
                            "             SEEAEES             ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "     S                     S     ",
                            "     E                     E     ",
                            "  CC E                     E CC  ",
                            "    DA                     AD    ",
                            "  CC E                     E CC  ",
                            "     E                     E     ",
                            "     S                     S     ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "             SEEAEES             ",
                            "                D                ",
                            "               C C               ",
                            "               C C               ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "                D                ",
                            "                A                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "  C                           C  ",
                            "   DA                       AD   ",
                            "  C                           C  ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                A                ",
                            "                D                ",
                            "               C C               ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "                D                ",
                            "                A                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            " CC                           CC ",
                            "   DA                       AD   ",
                            " CC                           CC ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                A                ",
                            "                D                ",
                            "               C C               ",
                            "               C C               ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "               C C               ",
                            "                D                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            " C                             C ",
                            "  D                           D  ",
                            " C                             C ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                D                ",
                            "               C C               ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "               C C               ",
                            "                D                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            " C                             C ",
                            "  D                           D  ",
                            " C                             C ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                D                ",
                            "               C C               ",
                            "                                 "
                        },
                        {
                            "             CCCCCCC             ",
                            "               C C               ",
                            "             DDDDDDD             ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "  D                           D  ",
                            "  D                           D  ",
                            "CCD                           DCC",
                            "  D                           D  ",
                            "CCD                           DCC",
                            "  D                           D  ",
                            "  D                           D  ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "             DDDDDDD             ",
                            "               C C               ",
                            "               C C               "
                        },
                        {
                            "            CCHHHHHCC            ",
                            "              DDDDD              ",
                            "            DD     DD            ",
                            "                                 ",
                            "                                 ",
                            "       S                 S       ",
                            "                                 ",
                            "     S                     S     ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "  D                           D  ",
                            "  D                           D  ",
                            " D                             D ",
                            "CD                             DC",
                            " D                             D ",
                            "CD                             DC",
                            " D                             D ",
                            "  D                           D  ",
                            "  D                           D  ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "     S                     S     ",
                            "                                 ",
                            "       S                 S       ",
                            "                                 ",
                            "                                 ",
                            "            DD     DD            ",
                            "              DDDDD              ",
                            "               C C               "
                        },
                        {
                            "            CHHHHHHHC            ",
                            "             DDCDCDD             ",
                            "            D  EEE  D            ",
                            "                                 ",
                            "      C                   C      ",
                            "       E                 E       ",
                            "    C                       C    ",
                            "     E                     E     ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "  D                           D  ",
                            " D                             D ",
                            " D                             D ",
                            "CCE                           ECC",
                            " DE                           ED ",
                            "CCE                           ECC",
                            " D                             D ",
                            " D                             D ",
                            "  D                           D  ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "     E                     E     ",
                            "    C                       C    ",
                            "       E                 E       ",
                            "      C                   C      ",
                            "                                 ",
                            "            D  EEE  D            ",
                            "             DDCDCDD             ",
                            "               C C               "
                        },
                        {
                            "            CHHCCCHHC            ",
                            "         CCCCDCCDCCDCCCC         ",
                            "       CCC  D EAAAE D  CCC       ",
                            "      CC                 CC      ",
                            "     CC                   CC     ",
                            "    CC E                 E CC    ",
                            "   CC                       CC   ",
                            "  CC E                     E CC  ",
                            "  C                           C  ",
                            " CC                           CC ",
                            " C                             C ",
                            " C                             C ",
                            "CCD                           DCC",
                            "CD                             DC",
                            "CCE                           ECC",
                            "CCA                           ACC",
                            "CDA                           ADC",
                            "CCA                           ACC",
                            "CCE                           ECC",
                            "CD                             DC",
                            "CCD                           DCC",
                            " C                             C ",
                            " C                             C ",
                            " CC                           CC ",
                            "  C                           C  ",
                            "  CC E                     E CC  ",
                            "   CC                       CC   ",
                            "    CC E                 E CC    ",
                            "     CC                   CC     ",
                            "      CC                 CC      ",
                            "       CCC  D EAAAE D  CCC       ",
                            "         CCCCDCCDCCDCCCC         ",
                            "            CCCCCCCCC            "
                        },
                        {
                            "            CHHC~CHHC            ",
                            "             DDDDDDD             ",
                            "          DDD EAAAE DDD          ",
                            "        DD             DD        ",
                            "      CDAA             AADC      ",
                            "      DA                 AD      ",
                            "    CDA                   ADC    ",
                            "    DA                     AD    ",
                            "   DA                       AD   ",
                            "   DA                       AD   ",
                            "  D                           D  ",
                            "  D                           D  ",
                            "  D                           D  ",
                            " D                             D ",
                            " DE                           ED ",
                            "CDA                           ADC",
                            " DA                           AD ",
                            "CDA                           ADC",
                            " DE                           ED ",
                            " D                             D ",
                            "  D                           D  ",
                            "  D                           D  ",
                            "  D                           D  ",
                            "   DA                       AD   ",
                            "   DA                       AD   ",
                            "    DA                     AD    ",
                            "    CDA                   ADC    ",
                            "      DA                 AD      ",
                            "      CDAA             AADC      ",
                            "        DD             DD        ",
                            "          DDD EAAAE DDD          ",
                            "             DDDDDDD             ",
                            "               C C               "
                        },
                        {
                            "            CHHCCCHHC            ",
                            "         CCCCDCCDCCDCCCC         ",
                            "       CCC  D EAAAE D  CCC       ",
                            "      CC                 CC      ",
                            "     CC                   CC     ",
                            "    CC E                 E CC    ",
                            "   CC                       CC   ",
                            "  CC E                     E CC  ",
                            "  C                           C  ",
                            " CC                           CC ",
                            " C                             C ",
                            " C                             C ",
                            "CCD                           DCC",
                            "CD                             DC",
                            "CCE                           ECC",
                            "CCA                           ACC",
                            "CDA                           ADC",
                            "CCA                           ACC",
                            "CCE                           ECC",
                            "CD                             DC",
                            "CCD                           DCC",
                            " C                             C ",
                            " C                             C ",
                            " CC                           CC ",
                            "  C                           C  ",
                            "  CC E                     E CC  ",
                            "   CC                       CC   ",
                            "    CC E                 E CC    ",
                            "     CC                   CC     ",
                            "      CC                 CC      ",
                            "       CCC  D EAAAE D  CCC       ",
                            "         CCCCDCCDCCDCCCC         ",
                            "            CCCCCCCCC            "
                        },
                        {
                            "            CHHHHHHHC            ",
                            "             DDCDCDD             ",
                            "            D  EEE  D            ",
                            "                                 ",
                            "      C                   C      ",
                            "       E                 E       ",
                            "    C                       C    ",
                            "     E                     E     ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "  D                           D  ",
                            " D                             D ",
                            " D                             D ",
                            "CCE                           ECC",
                            " DE                           ED ",
                            "CCE                           ECC",
                            " D                             D ",
                            " D                             D ",
                            "  D                           D  ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "     E                     E     ",
                            "    C                       C    ",
                            "       E                 E       ",
                            "      C                   C      ",
                            "                                 ",
                            "            D  EEE  D            ",
                            "             DDCDCDD             ",
                            "               C C               "
                        },
                        {
                            "            CCHHHHHCC            ",
                            "              DDDDD              ",
                            "            DD     DD            ",
                            "                                 ",
                            "                                 ",
                            "       S                 S       ",
                            "                                 ",
                            "     S                     S     ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "  D                           D  ",
                            "  D                           D  ",
                            " D                             D ",
                            "CD                             DC",
                            " D                             D ",
                            "CD                             DC",
                            " D                             D ",
                            "  D                           D  ",
                            "  D                           D  ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "     S                     S     ",
                            "                                 ",
                            "       S                 S       ",
                            "                                 ",
                            "                                 ",
                            "            DD     DD            ",
                            "              DDDDD              ",
                            "               C C               "
                        },
                        {
                            "             CCCCCCC             ",
                            "               C C               ",
                            "             DDDDDDD             ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "  D                           D  ",
                            "  D                           D  ",
                            "CCD                           DCC",
                            "  D                           D  ",
                            "CCD                           DCC",
                            "  D                           D  ",
                            "  D                           D  ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "             DDDDDDD             ",
                            "               C C               ",
                            "               C C               "
                        },
                        {
                            "                                 ",
                            "               C C               ",
                            "                D                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            " C                             C ",
                            "  D                           D  ",
                            " C                             C ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                D                ",
                            "               C C               ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "               C C               ",
                            "                D                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            " C                             C ",
                            "  D                           D  ",
                            " C                             C ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                D                ",
                            "               C C               ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "                D                ",
                            "                A                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            " CC                           CC ",
                            "   DA                       AD   ",
                            " CC                           CC ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                A                ",
                            "                D                ",
                            "               C C               ",
                            "               C C               ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "                D                ",
                            "                A                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "  C                           C  ",
                            "   DA                       AD   ",
                            "  C                           C  ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                A                ",
                            "                D                ",
                            "               C C               ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "                D                ",
                            "             SEEAEES             ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "     S                     S     ",
                            "     E                     E     ",
                            "  CC E                     E CC  ",
                            "    DA                     AD    ",
                            "  CC E                     E CC  ",
                            "     E                     E     ",
                            "     S                     S     ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "             SEEAEES             ",
                            "                D                ",
                            "               C C               ",
                            "               C C               ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "              CCCCC              ",
                            "                D                ",
                            "                A                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "    C                       C    ",
                            "   CC                       CC   ",
                            "    CDA                   ADC    ",
                            "   CC                       CC   ",
                            "    C                       C    ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                A                ",
                            "                D                ",
                            "              CCCCC              ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "                D                ",
                            "             SEEAEES             ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "       S                 S       ",
                            "       E                 E       ",
                            "    CC E                 E CC    ",
                            "      DA                 AD      ",
                            "    CC E                 E CC    ",
                            "       E                 E       ",
                            "       S                 S       ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "             SEEAEES             ",
                            "                D                ",
                            "               C C               ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "              CCCCC              ",
                            "                D                ",
                            "                A                ",
                            "                A                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "      C                   C      ",
                            "     CC                   CC     ",
                            "      CDAA             AADC      ",
                            "     CC                   CC     ",
                            "      C                   C      ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                A                ",
                            "                A                ",
                            "                D                ",
                            "              CCCCC              ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "                D                ",
                            "                D                ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "      CC                 CC      ",
                            "        DD             DD        ",
                            "      CC                 CC      ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                D                ",
                            "                D                ",
                            "               C C               ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "                D                ",
                            "                D                ",
                            "             DDDDDDD             ",
                            "            DD     DD            ",
                            "            D  EEE  D            ",
                            "       CCC  D EAAAE D  CCC       ",
                            "          DDD EAAAE DDD          ",
                            "       CCC  D EAAAE D  CCC       ",
                            "            D  EEE  D            ",
                            "            DD     DD            ",
                            "             DDDDDDD             ",
                            "                D                ",
                            "                D                ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "              DDDDD              ",
                            "             DDCDCDD             ",
                            "         CCCCDCCDCCDCCCC         ",
                            "             DDDDDDD             ",
                            "         CCCCDCCDCCDCCCC         ",
                            "             DDCDCDD             ",
                            "              DDDDD              ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        },
                        {
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "            CCCCCCCCC            ",
                            "               C C               ",
                            "            CCCCCCCCC            ",
                            "               C C               ",
                            "               C C               ",
                            "               C C               ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 ",
                            "                                 "
                        }
                    }))
                    .addElement(
                            'A',
                            ofBlocksTiered(
                                    (block, meta) -> block
                                                    == TT_Block_SpacetimeCompressionFieldGenerators
                                                            .SpacetimeCompressionFieldGenerators
                                            ? meta
                                            : -1,
                                    ImmutableList.of(
                                            Pair.of(
                                                    TT_Block_SpacetimeCompressionFieldGenerators
                                                            .SpacetimeCompressionFieldGenerators,
                                                    0),
                                            Pair.of(
                                                    TT_Block_SpacetimeCompressionFieldGenerators
                                                            .SpacetimeCompressionFieldGenerators,
                                                    1),
                                            Pair.of(
                                                    TT_Block_SpacetimeCompressionFieldGenerators
                                                            .SpacetimeCompressionFieldGenerators,
                                                    2),
                                            Pair.of(
                                                    TT_Block_SpacetimeCompressionFieldGenerators
                                                            .SpacetimeCompressionFieldGenerators,
                                                    3),
                                            Pair.of(
                                                    TT_Block_SpacetimeCompressionFieldGenerators
                                                            .SpacetimeCompressionFieldGenerators,
                                                    4),
                                            Pair.of(
                                                    TT_Block_SpacetimeCompressionFieldGenerators
                                                            .SpacetimeCompressionFieldGenerators,
                                                    5),
                                            Pair.of(
                                                    TT_Block_SpacetimeCompressionFieldGenerators
                                                            .SpacetimeCompressionFieldGenerators,
                                                    6),
                                            Pair.of(
                                                    TT_Block_SpacetimeCompressionFieldGenerators
                                                            .SpacetimeCompressionFieldGenerators,
                                                    7),
                                            Pair.of(
                                                    TT_Block_SpacetimeCompressionFieldGenerators
                                                            .SpacetimeCompressionFieldGenerators,
                                                    8)),
                                    -1,
                                    (t, meta) -> t.spacetimeCompressionFieldMetadata = meta,
                                    t -> t.spacetimeCompressionFieldMetadata))
                    .addElement(
                            'S',
                            ofBlocksTiered(
                                    (block, meta) ->
                                            block == TT_Block_StabilisationFieldGenerators.StabilisationFieldGenerators
                                                    ? meta
                                                    : -1,
                                    ImmutableList.of(
                                            Pair.of(
                                                    TT_Block_StabilisationFieldGenerators.StabilisationFieldGenerators,
                                                    0),
                                            Pair.of(
                                                    TT_Block_StabilisationFieldGenerators.StabilisationFieldGenerators,
                                                    1),
                                            Pair.of(
                                                    TT_Block_StabilisationFieldGenerators.StabilisationFieldGenerators,
                                                    2),
                                            Pair.of(
                                                    TT_Block_StabilisationFieldGenerators.StabilisationFieldGenerators,
                                                    3),
                                            Pair.of(
                                                    TT_Block_StabilisationFieldGenerators.StabilisationFieldGenerators,
                                                    4),
                                            Pair.of(
                                                    TT_Block_StabilisationFieldGenerators.StabilisationFieldGenerators,
                                                    5),
                                            Pair.of(
                                                    TT_Block_StabilisationFieldGenerators.StabilisationFieldGenerators,
                                                    6),
                                            Pair.of(
                                                    TT_Block_StabilisationFieldGenerators.StabilisationFieldGenerators,
                                                    7),
                                            Pair.of(
                                                    TT_Block_StabilisationFieldGenerators.StabilisationFieldGenerators,
                                                    8)),
                                    -1,
                                    (t, meta) -> t.stabilisationFieldMetadata = meta,
                                    t -> t.stabilisationFieldMetadata))
                    .addElement('C', ofBlock(sBlockCasingsBA0, 11))
                    .addElement('D', ofBlock(sBlockCasingsBA0, 10))
                    .addElement(
                            'E',
                            ofBlocksTiered(
                                    (block, meta) -> block
                                                    == TT_Block_TimeAccelerationFieldGenerators
                                                            .TimeAccelerationFieldGenerator
                                            ? meta
                                            : -1,
                                    ImmutableList.of(
                                            Pair.of(
                                                    TT_Block_TimeAccelerationFieldGenerators
                                                            .TimeAccelerationFieldGenerator,
                                                    0),
                                            Pair.of(
                                                    TT_Block_TimeAccelerationFieldGenerators
                                                            .TimeAccelerationFieldGenerator,
                                                    1),
                                            Pair.of(
                                                    TT_Block_TimeAccelerationFieldGenerators
                                                            .TimeAccelerationFieldGenerator,
                                                    2),
                                            Pair.of(
                                                    TT_Block_TimeAccelerationFieldGenerators
                                                            .TimeAccelerationFieldGenerator,
                                                    3),
                                            Pair.of(
                                                    TT_Block_TimeAccelerationFieldGenerators
                                                            .TimeAccelerationFieldGenerator,
                                                    4),
                                            Pair.of(
                                                    TT_Block_TimeAccelerationFieldGenerators
                                                            .TimeAccelerationFieldGenerator,
                                                    5),
                                            Pair.of(
                                                    TT_Block_TimeAccelerationFieldGenerators
                                                            .TimeAccelerationFieldGenerator,
                                                    6),
                                            Pair.of(
                                                    TT_Block_TimeAccelerationFieldGenerators
                                                            .TimeAccelerationFieldGenerator,
                                                    7),
                                            Pair.of(
                                                    TT_Block_TimeAccelerationFieldGenerators
                                                            .TimeAccelerationFieldGenerator,
                                                    8)),
                                    -1,
                                    (t, meta) -> t.timeAccelerationFieldMetadata = meta,
                                    t -> t.timeAccelerationFieldMetadata))
                    .addElement(
                            'H',
                            ofHatchAdderOptional(
                                    GT_MetaTileEntity_EM_EyeOfHarmony::addClassicToMachineList,
                                    textureOffset,
                                    1,
                                    sBlockCasingsBA0,
                                    12))
                    .build();

    private double hydrogenOverflowProbabilityAdjustment;
    private double heliumOverflowProbabilityAdjustment;

    // Maximum additional chance of recipe success that can be obtained from adding computation.
    private static final double maxPercentageChanceGainFromComputationPerSecond = 0.3;

    // todo: make higher on final release.
    private static final long ticksBetweenHatchDrain = 20;

    private List<ItemStackLong> outputItems = new ArrayList<>();

    private void calculateHydrogenHeliumInputExcessValues(
            long hydrogen_recipe_requirement, long helium_recipe_requirement) {

        long hydrogen_stored = validFluidMap.get(Materials.Hydrogen.getGas(1L));
        long helium_stored = validFluidMap.get(Materials.Helium.getGas(1L));

        double hydrogen_excess_percentage = abs(1 - hydrogen_stored / hydrogen_recipe_requirement);
        double helium_excess_percentage = abs(1 - helium_stored / helium_recipe_requirement);

        hydrogenOverflowProbabilityAdjustment = 1 - exp(-pow(30 * hydrogen_excess_percentage, 2));
        heliumOverflowProbabilityAdjustment = 1 - exp(-pow(30 * helium_excess_percentage, 2));
    }

    private double recipeChanceCalculator() {
        double chance = (currentRecipe.getBaseRecipeSuccessChance()
                - timeAccelerationFieldMetadata * 0.1
                + stabilisationFieldMetadata * 0.05
                - hydrogenOverflowProbabilityAdjustment
                - heliumOverflowProbabilityAdjustment
                + maxPercentageChanceGainFromComputationPerSecond * (1 - exp(-10e-5 * getComputation())));

        return clamp(chance, 0.0, 1.0);
    }

    public static double clamp(double amount, double min, double max) {
        return Math.max(min, Math.min(amount, max));
    }

    private double recipeYieldCalculator() {
        double yield = 1.0
                - hydrogenOverflowProbabilityAdjustment
                - heliumOverflowProbabilityAdjustment
                - stabilisationFieldMetadata * 0.05;

        return clamp(yield, 0.0, 1.0);
    }

    private int recipeProcessTimeCalculator(long recipeTime, long recipeSpacetimeCasingRequired) {

        // Tier 1 recipe.
        // Tier 2 spacetime blocks.
        // = 3% discount.

        // Tier 1 recipe.
        // Tier 3 spacetime blocks.
        // = 3%*3% = 5.91% discount.

        long spacetimeCasingDifference = (recipeSpacetimeCasingRequired - spacetimeCompressionFieldMetadata);
        double recipeTimeDiscounted = recipeTime
                * pow(2.0, -timeAccelerationFieldMetadata)
                * pow(1 - spacetimeCasingDifferenceDiscountPercentage, spacetimeCasingDifference);
        return (int) Math.max(recipeTimeDiscounted, 1.0);
    }

    @Override
    public IStructureDefinition<GT_MetaTileEntity_EM_EyeOfHarmony> getStructure_EM() {
        return STRUCTURE_DEFINITION;
    }

    public GT_MetaTileEntity_EM_EyeOfHarmony(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_EM_EyeOfHarmony(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_EM_EyeOfHarmony(mName);
    }

    @Override
    public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {

        spacetimeCompressionFieldMetadata = -1;
        timeAccelerationFieldMetadata = -1;
        stabilisationFieldMetadata = -1;

        // Check structure of multi.
        if (!structureCheck_EM("main", 16, 16, 0)) {
            return false;
        }

        // Check if there is 1+ output bus, and they are ME output busses.
        {
            if (mOutputBusses.size() == 0) {
                return false;
            }

            for (GT_MetaTileEntity_Hatch_OutputBus hatch : mOutputBusses) {
                if (!(hatch instanceof GT_MetaTileEntity_Hatch_OutputBus_ME)) {
                    return false;
                }
            }
        }

        // Check if there is 1+ output hatch, and they are ME output hatches.
        {
            if (mOutputHatches.size() == 0) {
                return false;
            }

            for (GT_MetaTileEntity_Hatch_Output hatch : mOutputHatches) {
                if (!(hatch instanceof GT_MetaTileEntity_Hatch_Output_ME)) {
                    return false;
                }
            }
        }

        // Make sure there are no energy hatches.
        {
            if (mEnergyHatches.size() > 0) {
                return false;
            }

            if (mExoticEnergyHatches.size() > 0) {
                return false;
            }
        }

        // Make sure there are 2 input hatches.
        if (mInputHatches.size() != 2) {
            return false;
        }

        // 1 Maintenance hatch, as usual.
        return (mMaintenanceHatches.size() == 1);
    }

    @Override
    public GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Spacetime Manipulator")
                .addInfo(GOLD + "--------------------------------------------------------------------------------")
                .addInfo("Creates a pocket of spacetime that is bigger on the inside using transdimensional")
                .addInfo("engineering. Certified Time Lord regulation compliant. This multi uses too much EU")
                .addInfo("to be handled with conventional means. All EU requirements are handled directly by your")
                .addInfo("your wireless EU network.")
                .addInfo(GOLD + "--------------------------------------------------------------------------------")
                .addInfo("This multiblock will constantly consume hydrogen and helium when it is not running a")
                .addInfo("recipe as fast as it can. It will store this internally, you can see the totals by")
                .addInfo("using a scanner. This multi also has three tiered blocks with " + RED + 9 + GRAY + " tiers")
                .addInfo("each. They are as follows and have the associated effects on the multi.")
                .addInfo(BLUE + "Spacetime Compression Field Generator:")
                .addInfo("- The tier of this block determines what recipes can be run. If the multiblocks")
                .addInfo("  spacetime compression field block exceeds the requirements of the recipe it")
                .addInfo("  will decrease the processing time by " + RED + "3%" + GRAY
                        + " per tier over the requirement. This")
                .addInfo("  is multiplicative.")
                .addInfo(BLUE + "Time Dilation Field Generator:")
                .addInfo("- Decreases the time required by a recipe by a factor of " + RED + "2" + GRAY
                        + " per tier of block.")
                .addInfo("  Decreases the probability of a recipe succeeding by " + RED + "10%" + GRAY
                        + " per tier (additive)")
                .addInfo(BLUE + "Stabilisation Field Generator:")
                .addInfo("- Increases the probability of a recipe succeeding by " + RED + "5%" + GRAY
                        + " per tier (additive).")
                .addInfo("  Decreases the yield of a recipe by " + RED + "5%" + GRAY + " per tier (additive). ")
                .addInfo(GOLD + "--------------------------------------------------------------------------------")
                .addInfo("Computation/s provided to the multiblock can increase the chance by up to " + RED
                        + formatNumbers(maxPercentageChanceGainFromComputationPerSecond * 100) + GRAY
                        + "%.")
                .addInfo("The associated formula is " + GREEN
                        + "additional_chance = 0.3 * exp(10^(-5) * computation_per_second)" + GRAY + ".")
                .addInfo(GOLD + "--------------------------------------------------------------------------------")
                .addInfo("Going over a recipe requirement on hydrogen or helium has a penalty on yield and recipe")
                .addInfo(
                        "chance. All stored hydrogen and helium is consumed during a craft. The associated formulas are:")
                .addInfo(GREEN + "percentage_overflow = abs(1 - fluid_stored/recipe_requirement)")
                .addInfo(GREEN + "adjustment_value = 1 - exp(-(30 * percentage_overflow)^2)")
                .addInfo("The value of adjustment_value is then subtracted from the total yield and recipe chance.")
                .addInfo(GOLD + "--------------------------------------------------------------------------------")
                .addInfo("It should be noted that base recipe chance is determined per recipe and yield always")
                .addInfo("starts at 1 and subtracts depending on penalities. All fluid/item outputs are multiplied")
                .addInfo("by the yield calculated.")
                .addInfo(GOLD + "--------------------------------------------------------------------------------")
                .addInfo("This multiblock can only output to ME output busses/hatches. If no space in the network")
                .addInfo(
                        "is avaliable the items/fluids will be " + UNDERLINE + DARK_RED + "voided" + RESET + GRAY + ".")
                .addInfo(GOLD + "--------------------------------------------------------------------------------")
                .addInfo("Recipes that fail will return a random amount of the fluid back from the recipe and some")
                .addInfo("exotic material that rejects conventional physics.")
                .addSeparator()
                .addStructureInfo("Eye of Harmony structure is too complex! See schematic for details.")
                .addStructureInfo(EnumChatFormatting.GOLD + "896" + EnumChatFormatting.GRAY
                        + " Reinforced Spacetime Structure Casing.")
                .addStructureInfo(EnumChatFormatting.GOLD + "534" + EnumChatFormatting.GRAY
                        + " Ultimate Temporal Boundary Casing.")
                .addStructureInfo(
                        EnumChatFormatting.GOLD + "680" + EnumChatFormatting.GRAY + " Time Dilation Field Generator.")
                .addStructureInfo(
                        EnumChatFormatting.GOLD + "48" + EnumChatFormatting.GRAY + " Stabilisation Field Generator.")
                .addStructureInfo(EnumChatFormatting.GOLD + "138" + EnumChatFormatting.GRAY
                        + " Spacetime Compression Field Generator.")
                .addStructureInfo("--------------------------------------------")
                .addStructureInfo(
                        "Requires " + EnumChatFormatting.GOLD + 1 + EnumChatFormatting.GRAY + " maintenance hatch.")
                .addStructureInfo(
                        "Requires " + EnumChatFormatting.GOLD + 2 + EnumChatFormatting.GRAY + " input hatches.")
                .addStructureInfo(
                        "Requires " + EnumChatFormatting.GOLD + 1 + EnumChatFormatting.GRAY + "+ ME output hatch.")
                .addStructureInfo(
                        "Requires " + EnumChatFormatting.GOLD + 1 + EnumChatFormatting.GRAY + " input busses.")
                .addStructureInfo(
                        "Requires " + EnumChatFormatting.GOLD + 1 + EnumChatFormatting.GRAY + " ME output bus.")
                .addStructureInfo("--------------------------------------------")
                .beginStructureBlock(33, 33, 33, false)
                .toolTipFinisher(AuthorColen.substring(8) + EnumChatFormatting.GRAY + "&" + CommonValues.TEC_MARK_EM);
        return tt;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister aBlockIconRegister) {
        ScreenOFF = new Textures.BlockIcons.CustomIcon("iconsets/EM_BHG");
        ScreenON = new Textures.BlockIcons.CustomIcon("iconsets/EM_BHG_ACTIVE");
        super.registerIcons(aBlockIconRegister);
    }

    @Override
    public ITexture[] getTexture(
            IGregTechTileEntity aBaseMetaTileEntity,
            byte aSide,
            byte aFacing,
            byte aColorIndex,
            boolean aActive,
            boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[] {
                Textures.BlockIcons.casingTexturePages[texturePage][12],
                new TT_RenderedExtendedFacingTexture(aActive ? ScreenON : ScreenOFF)
            };
        }
        return new ITexture[] {Textures.BlockIcons.casingTexturePages[texturePage][12]};
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {

        structureBuild_EM("main", 16, 16, 0, stackSize, hintsOnly);
    }

    private final Map<FluidStack, Long> validFluidMap = new HashMap<FluidStack, Long>() {
        {
            put(Materials.Hydrogen.getGas(1), 0L);
            put(Materials.Helium.getGas(1), 0L);
        }
    };

    private void drainFluidFromHatchesAndStoreInternally() {
        for (GT_MetaTileEntity_Hatch_Input inputHatch : mInputHatches) {
            FluidStack fluidInHatch = inputHatch.getFluid();

            if (fluidInHatch == null) {
                continue;
            }

            // Iterate over valid fluids and store them in a hashmap.
            for (FluidStack validFluid : validFluidMap.keySet()) {
                if (fluidInHatch.isFluidEqual(validFluid)) {
                    validFluidMap.put(validFluid, validFluidMap.get(validFluid) + (long) fluidInHatch.amount);
                    inputHatch.setFillableStack(null);
                }
            }
        }
    }

    private EyeOfHarmonyRecipe currentRecipe;

    // Counter for lag prevention.
    private long lagPreventer = 0;

    // Check for recipe every recipeCheckInterval ticks.
    private final long recipeCheckInterval = 3 * 20;

    @Override
    public boolean checkRecipe_EM(ItemStack aStack) {
        if (aStack == null) {
            return false;
        }

        lagPreventer++;
        if (lagPreventer < recipeCheckInterval) {
            lagPreventer = 0;
            // No item in multi gui slot.

            currentRecipe = eyeOfHarmonyRecipeStorage.recipeLookUp(aStack);
            if (processRecipe(currentRecipe)) {
                return true;
            }

            currentRecipe = null;
        }
        return false;
    }

    private long getHydrogenStored() {
        return validFluidMap.get(Materials.Hydrogen.getGas(1));
    }

    private long getHeliumStored() {
        return validFluidMap.get(Materials.Helium.getGas(1));
    }

    public boolean processRecipe(EyeOfHarmonyRecipe recipeObject) {

        //        if ((getHydrogenStored() < currentRecipe.getHydrogenRequirement())
        //                || (getHeliumStored() < currentRecipe.getHeliumRequirement())) {
        //            return false;
        //        }

        // todo: DEBUG, DELETE THIS:
        if ((getHydrogenStored() < 100) || (getHeliumStored() < 100)) {
            return false;
        }

        // Check tier of spacetime compression blocks is high enough.
        if (spacetimeCompressionFieldMetadata < recipeObject.getSpacetimeCasingTierRequired()) {
            return false;
        }

        // Remove EU from the users network.
        if (!addEUToGlobalEnergyMap(userUUID, -recipeObject.getEUStartCost())) {
            return false;
        }

        mMaxProgresstime = recipeProcessTimeCalculator(
                recipeObject.getRecipeTimeInTicks(), recipeObject.getSpacetimeCasingTierRequired());

        calculateHydrogenHeliumInputExcessValues(
                recipeObject.getHydrogenRequirement(), recipeObject.getHeliumRequirement());

        // todo: DEBUG ! DELETE THESE TWO LINES:
        hydrogenOverflowProbabilityAdjustment = 0;
        heliumOverflowProbabilityAdjustment = 0;

        successChance = recipeChanceCalculator();

        // Determine EU recipe output.
        euOutput = recipeObject.getEUOutput();

        // Set expected recipe computation.
        eRequiredData = getComputation();

        // Reduce internal storage by hydrogen and helium quantity required for recipe.
        validFluidMap.put(Materials.Hydrogen.getGas(1), 0L);
        validFluidMap.put(Materials.Helium.getGas(1), 0L);

        double yield = recipeYieldCalculator();
        successChance = 1; // todo debug, remove.

        // Return copies of the output objects.
        mOutputFluids = recipeObject.getOutputFluids();
        outputItems = recipeObject.getOutputItems();

        if (yield != 1.0) {
            // Iterate over item output list and apply yield values.
            for (ItemStackLong itemStackLong : outputItems) {
                itemStackLong.stackSize *= yield;
            }

            // Iterate over fluid output list and apply yield values.
            for (FluidStack fluidStack : mOutputFluids) {
                fluidStack.amount *= yield;
            }
        }

        updateSlots();

        createRenderBlock();

        recipeRunning = true;
        return true;
    }

    private void createRenderBlock() {

        IGregTechTileEntity gregTechTileEntity = this.getBaseMetaTileEntity();

        int x = gregTechTileEntity.getXCoord();
        int y = gregTechTileEntity.getYCoord();
        int z = gregTechTileEntity.getZCoord();

        double xOffset = 16 * getExtendedFacing().getRelativeBackInWorld().offsetX;
        double zOffset = 16 * getExtendedFacing().getRelativeBackInWorld().offsetZ;
        double yOffset = 16 * getExtendedFacing().getRelativeBackInWorld().offsetZ;

        this.getBaseMetaTileEntity()
                .getWorld()
                .setBlock((int) (x + xOffset), (int) (y + yOffset), (int) (z + zOffset), Blocks.air);
        this.getBaseMetaTileEntity()
                .getWorld()
                .setBlock((int) (x + xOffset), (int) (y + yOffset), (int) (z + zOffset), eyeOfHarmonyRenderBlock);
        TileEyeOfHarmony rendererTileEntity = (TileEyeOfHarmony)
                this.getBaseMetaTileEntity().getWorld().getTileEntity((int) (x + xOffset), (int) (y + yOffset), (int)
                        (z + zOffset));

        int recipeSpacetimeTier = (int) currentRecipe.getSpacetimeCasingTierRequired();

        // Star is a larger size depending on the spacetime tier of the recipe.
        rendererTileEntity.setSize((1 + recipeSpacetimeTier));

        // Star rotates faster the higher tier time dilation you use in the multi.
        // Lower value = faster rotation speed.
        rendererTileEntity.setRotationSpeed((1 + timeAccelerationFieldMetadata)/2.0f);

        // Colour of tier determined by star tier.
        Color colour = getStarColour((int) currentRecipe.getRocketTier());
        rendererTileEntity.setColour(colour);

        // Set recipe spacetime tier for usage elsewhere.
        rendererTileEntity.setTier(currentRecipe.getRocketTier());
        rendererTileEntity.setOrbitingBody(
                Block.getBlockFromItem(currentRecipe.getRecipeTriggerItem().getItem()));
    }

    private static final Color redStar = new Color(155, 9, 38);
    private static final Color orangeStar = new Color(190, 85, 9);
    private static final Color blueStar = new Color(96, 152, 234);
    private static final Color whiteStar = new Color(200, 200, 200);
    private static final Color blackHole = new Color(0, 0, 0);
    public static final Color errorStar = new Color(222, 0, 255);

    Color getStarColour(final int tier) {
        switch (tier) {
            case 0:
            case 1:
                return redStar;
            case 2:
            case 3:
                return orangeStar;
            case 4:
            case 5:
                return blueStar;
            case 6:
            case 7:
            case 8:
                return whiteStar;
            case 9:
                return blackHole;
            default:
                return errorStar;
        }
    }

    private double successChance;

    private void outputFailedChance() {
        // todo Replace with proper fluid once added to GT.
        int exoticMaterialOutputAmount =
                (int) ((successChance) * 1440 * (getHydrogenStored() + getHeliumStored()) / 1_000_000_000.0);
        mOutputFluids = new FluidStack[] {Materials.SpaceTime.getFluid(exoticMaterialOutputAmount)};
        super.outputAfterRecipe_EM();
    }

    @Override
    public void stopMachine() {
        super.stopMachine();
        recipeRunning = false;
    }

    private void destroyRenderBlock() {
        IGregTechTileEntity gregTechTileEntity = this.getBaseMetaTileEntity();

        int x = gregTechTileEntity.getXCoord();
        int y = gregTechTileEntity.getYCoord();
        int z = gregTechTileEntity.getZCoord();

        double xOffset = 16 * getExtendedFacing().getRelativeBackInWorld().offsetX;
        double zOffset = 16 * getExtendedFacing().getRelativeBackInWorld().offsetZ;
        double yOffset = 16 * getExtendedFacing().getRelativeBackInWorld().offsetZ;

        this.getBaseMetaTileEntity()
                .getWorld()
                .setBlock((int) (x + xOffset), (int) (y + yOffset), (int) (z + zOffset), Blocks.air);
    }

    public void outputAfterRecipe_EM() {
        recipeRunning = false;
        eRequiredData = 0L;

        destroyRenderBlock();

        if (successChance < random()) {
            outputFailedChance();
            outputItems = new ArrayList<>();
            return;
        }

        addEUToGlobalEnergyMap(userUUID, euOutput);
        euOutput = 0;

        for (ItemStackLong itemStack : outputItems) {
            outputItemToAENetwork(itemStack.itemStack, itemStack.stackSize);
        }

        // Clear the array list for new recipes.
        outputItems = new ArrayList<>();

        // Do other stuff from TT superclasses. E.g. outputting fluids.
        super.outputAfterRecipe_EM();
    }

    // todo probably remove me.
    private void pushComputation() {
        if (computationStack.size() == computationTickCacheSize) {
            computationStack.remove(0);
        }
        computationStack.push(eAvailableData);
    }

    @Override
    public void onPreTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPreTick(aBaseMetaTileEntity, aTick);

        if (aTick == 1) {
            userUUID = String.valueOf(getBaseMetaTileEntity().getOwnerUuid());
            userName = getBaseMetaTileEntity().getOwnerName();
            strongCheckOrAddUser(userUUID, userName);
        }

        // Add computation to stack. Prevents small interruptions causing issues.
        pushComputation();

        if (!recipeRunning) {
            if ((aTick % ticksBetweenHatchDrain) == 0) {
                drainFluidFromHatchesAndStoreInternally();
            }
        }
    }

    private boolean recipeRunning = false;
    private static final int computationTickCacheSize = 5;

    private long getComputation() {
        return Collections.max(computationStack);
    }

    // Will void if AE network is full.
    private void outputItemToAENetwork(ItemStack item, long amount) {

        if ((item == null) || (amount <= 0)) {
            return;
        }

        if (amount < Integer.MAX_VALUE) {
            ItemStack tmpItem = item.copy();
            tmpItem.stackSize = (int) amount;
            ((GT_MetaTileEntity_Hatch_OutputBus_ME) mOutputBusses.get(0)).store(tmpItem);
        } else {
            // For item stacks > Int max.
            while (amount >= Integer.MAX_VALUE) {
                ItemStack tmpItem = item.copy();
                tmpItem.stackSize = Integer.MAX_VALUE;
                ((GT_MetaTileEntity_Hatch_OutputBus_ME) mOutputBusses.get(0)).store(tmpItem);
                amount -= Integer.MAX_VALUE;
            }
            ItemStack tmpItem = item.copy();
            tmpItem.stackSize = (int) amount;
            ((GT_MetaTileEntity_Hatch_OutputBus_ME) mOutputBusses.get(0)).store(tmpItem);
        }
    }

    @Override
    public String[] getInfoData() {
        ArrayList<String> str = new ArrayList<>(Arrays.asList(super.getInfoData()));
        str.add(GOLD + "---------------- Control Block Statistics ----------------");
        str.add("Spacetime Compression Field Grade: " + BLUE + spacetimeCompressionFieldMetadata);
        str.add("Time Dilation Field Grade: " + BLUE + timeAccelerationFieldMetadata);
        str.add("Stabilisation Field Grade: " + BLUE + stabilisationFieldMetadata);
        str.add(GOLD + "----------------- Internal Fluids Stored ----------------");
        validFluidMap.forEach(
                (key, value) -> str.add(BLUE + key.getLocalizedName() + RESET + " : " + RED + formatNumbers(value)));
        if (recipeRunning) {
            str.add(GOLD + "---------------------- Other Stats ---------------");
            str.add("Recipe Success Chance: " + RED + formatNumbers(100 * successChance) + RESET + "%");
            str.add("Recipe Yield: " + RED + formatNumbers(100 * successChance) + RESET + "%");
            str.add("EU Output: " + RED + formatNumbers(euOutput) + RESET + " EU");
            if (mOutputFluids.length > 0) {
                // Star matter is always the last element in the array.
                str.add("Estimated Star Matter Output: " + RED
                        + formatNumbers(mOutputFluids[mOutputFluids.length - 1].amount) + RESET + " L");
            }
            long euPerTick = euOutput / maxProgresstime();
            if (euPerTick < LongMath.pow(10, 12)) {
                str.add("Estimated EU/t: " + RED + formatNumbers(euOutput / maxProgresstime()) + RESET + " EU/t");
            } else {
                str.add("Estimated EU/t: " + RED
                        + ReadableNumberConverter.INSTANCE.toWideReadableForm(euOutput / maxProgresstime()) + RESET
                        + " EU/t");
            }
            str.add(GOLD + "-----------------------------------------------------");
        }
        return str.toArray(new String[0]);
    }

    @Override
    public String[] getStructureDescription(ItemStack stackSize) {
        return new String[] {"Eye of Harmony multiblock"};
    }

    // NBT save/load strings.
    private static final String eyeOfHarmony = "eyeOfHarmonyOutput";
    private static final String numberOfItemsNBTTag = eyeOfHarmony + "numberOfItems";
    private static final String itemOutputNBTTag = eyeOfHarmony + "itemOutput";
    private static final String recipeRunningNBTTag = eyeOfHarmony + "recipeRunning";
    private static final String recipeEUOutputNBTTag = eyeOfHarmony + "euOutput";
    private static final String recipeSuccessChanceNBTTag = eyeOfHarmony + "recipeSuccessChance";

    // Sub tags, less specific names required.
    private static final String stackSizeNBTTag = "stackSize";
    private static final String itemStackNBTTag = "itemStack";

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        // Save the quantity of fluid stored inside the controller.
        validFluidMap.forEach((key, value) -> aNBT.setLong("stored." + key.getUnlocalizedName(), value));

        aNBT.setBoolean(recipeRunningNBTTag, recipeRunning);
        aNBT.setLong(recipeEUOutputNBTTag, euOutput);
        aNBT.setDouble(recipeSuccessChanceNBTTag, successChance);

        // Store damage values/stack sizes of GT items being outputted.
        NBTTagCompound itemStackListNBTTag = new NBTTagCompound();
        itemStackListNBTTag.setLong(numberOfItemsNBTTag, outputItems.size());

        int index = 0;
        for (ItemStackLong itemStackLong : outputItems) {
            // Save stack size to NBT.
            itemStackListNBTTag.setLong(index + stackSizeNBTTag, itemStackLong.stackSize);

            // Save ItemStack to NBT.
            aNBT.setTag(index + itemStackNBTTag, itemStackLong.itemStack.writeToNBT(new NBTTagCompound()));

            index++;
        }

        aNBT.setTag(itemOutputNBTTag, itemStackListNBTTag);

        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(final NBTTagCompound aNBT) {

        // Load the quantity of fluid stored inside the controller.
        validFluidMap.forEach(
                (key, value) -> validFluidMap.put(key, aNBT.getLong("stored." + key.getUnlocalizedName())));

        // Load other stuff from NBT.
        recipeRunning = aNBT.getBoolean(recipeRunningNBTTag);
        euOutput = aNBT.getLong(recipeEUOutputNBTTag);
        successChance = aNBT.getDouble(recipeSuccessChanceNBTTag);

        // Load damage values/stack sizes of GT items being outputted and convert back to items.
        NBTTagCompound tempItemTag = aNBT.getCompoundTag(itemOutputNBTTag);

        // Iterate over all stored items.
        for (int index = 0; index < tempItemTag.getInteger(numberOfItemsNBTTag); index++) {

            // Load stack size from NBT.
            long stackSize = tempItemTag.getLong(index + stackSizeNBTTag);

            // Load ItemStack from NBT.
            ItemStack itemStack = ItemStack.loadItemStackFromNBT(aNBT.getCompoundTag(index + itemStackNBTTag));

            outputItems.add(new ItemStackLong(itemStack, stackSize));
        }

        super.loadNBTData(aNBT);
    }
}
