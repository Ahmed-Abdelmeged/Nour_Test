package com.abdelmeged.ahmed.nourplayer.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.PUT;

/**
 * Created by ahmed on 7/20/2017.
 */

public class Constants {
    /**
     * Key for get the Sura extra intent from the MainActivity
     */
    public static final String EXTRA_SURA = "extra-sura";

    public static final String BASE_URL = "https://drive.google.com/";

    public static final String EXTRA_SURA_DOWNLOAD = "extra-sura-download-id";

    public static final String MESSAGE_PROGRESS = "message_progress";


    /**
     * Surahs
     */
    //1-29
    private static final List<String> FTA = Arrays.asList("الفاتحه", "فاتحه", "الفاتحة", "فاتحة", "فتحه", "فتحة");
    private static final List<String> BQR = Arrays.asList("البقره", "البقرة", "بقره", "بكره", "بقرة", "بكرة");
    private static final List<String> IMR = Arrays.asList("ال عمران", "العمران", "عمران");
    private static final List<String> NSA = Arrays.asList("النساء", "نساء");
    private static final List<String> MDA = Arrays.asList("المائده", "مائده", "المائدة", "مائدة");
    private static final List<String> ANM = Arrays.asList("انعام", "الأنعام", "الانعام", "أنعام", "انعم", "أنعم");
    private static final List<String> ARF = Arrays.asList("الاعراف", "الأعراف", "اعراف", "الأعرف", "الاعرف", "أعراف");
    private static final List<String> ANF = Arrays.asList("الأنفال", "الانفال", "انفال", "أنفال");
    private static final List<String> TWB = Arrays.asList("التوبه", "توبه", "التوية", "توبة");
    private static final List<String> YNS = Arrays.asList("يونس", "اليونس", "يونص", "اليونص");
    private static final List<String> HUD = Arrays.asList("هود", "هوت", "الهود", "الهوت");
    private static final List<String> YSF = Arrays.asList("يوسف", "اليوسف", "يوصف", "اليوصف");
    private static final List<String> RAD = Arrays.asList("الرعد", "رعب", "الرعب", "رعد");
    private static final List<String> IBR = Arrays.asList("ابراهيم", "أبراهيم", "إبراهيم", "إبرهيم", "ابرهيم", "أبراهيم");
    private static final List<String> HJR = Arrays.asList("الحجر", "حجر");
    private static final List<String> NHL = Arrays.asList("النحل", "نحل");
    private static final List<String> ISR = Arrays.asList("الاسراء", "الأسراء", "الإسراء", "اسراء", "أسراء", "إسراء");
    private static final List<String> KHF = Arrays.asList("الكهف", "كهف");
    private static final List<String> MRY = Arrays.asList("مريم", "المريم");
    private static final List<String> THA = Arrays.asList("طه", "طة", "الطة", "الطه");
    private static final List<String> ANB = Arrays.asList("الانبياء", "الأنبياء", "الإنبياء", "انبياء", "أنبياء", "إنبياء");
    private static final List<String> HAJ = Arrays.asList("حج", "الحج");
    private static final List<String> MON = Arrays.asList("المؤمنون", "المؤمنين", "مؤمنين", "مؤمنون");
    private static final List<String> NOR = Arrays.asList("النور", "نور");
    private static final List<String> FUQ = Arrays.asList("فرقان", "الفرقان");
    private static final List<String> SHU = Arrays.asList("الشعراء", "شعراء");
    private static final List<String> NML = Arrays.asList("النمل", "نمل");
    private static final List<String> QAS = Arrays.asList("القصص", "قصص");
    private static final List<String> ANK = Arrays.asList("العنكبوت", "عنكبوت");


    //30-58
    private static final List<String> ROM = Arrays.asList("الروم", "روم");
    private static final List<String> LUQ = Arrays.asList("لقمان", "اللقمان", "لقمن");
    private static final List<String> SJD = Arrays.asList("السجده", "سجدة", "السجدة", "سجده");
    private static final List<String> SBA = Arrays.asList("الأحزاب", "الاحزاب", "الإحزاب", "احزاب", "أحزاب", "الأحذاب", "الاحذاب", "الإحذاب", "احذاب", "أحذاب", "إحذاب", "إحزاب");
    private static final List<String> AZB = Arrays.asList("سيا", "سبأ", "السبا", "السبأ");
    private static final List<String> FTR = Arrays.asList("فاطر", "الفاطر", "فطر", "الفطر");
    private static final List<String> YSN = Arrays.asList("يس", "اليس", "يسين", "اليسين", "يسن", "اليسن");
    private static final List<String> SFT = Arrays.asList("الصافات", "صافات", "صافت", "الصافت", "الصفات", "صفات");
    private static final List<String> SAD = Arrays.asList("ص", "الص", "صاد", "الصاد", "صد", "صوت","الصد");
    private static final List<String> ZMR = Arrays.asList("الزمر", "زمر", "ذمر", "الذمر");
    private static final List<String> GFR = Arrays.asList("غافر", "الغافر");
    private static final List<String> FST = Arrays.asList("فصلت", "الفصلت");
    private static final List<String> SRA = Arrays.asList("الشورى", "شورى");
    private static final List<String> ZRF = Arrays.asList("الزخرف", "زخرف", "الذخرف", "ذخرف");
    private static final List<String> DKN = Arrays.asList("الدخان", "دخان", "دخن", "الدخن");
    private static final List<String> GTY = Arrays.asList("الجاثيه", "جاثيه", "جثيه", "الجثيه", "الجاسيه", "جاسيه");
    private static final List<String> AGF = Arrays.asList("الاحقاف", "الأحقاف", "الإحقاف", "احقاف", "أحقاف", "إحقاف");
    private static final List<String> MHD = Arrays.asList("محمد", "المحمد");
    private static final List<String> FTH = Arrays.asList("الفتح", "فتح");
    private static final List<String> JRT = Arrays.asList("الحجرات", "حجرات", "الحجرت", "حجرت");
    private static final List<String> QAF = Arrays.asList("ق", "الق", "قف", "القاف", "قاف", "القف");
    private static final List<String> ZRT = Arrays.asList("الذاريات", "ذاريات", "الذريات", "ذريات", "الزاريات", "زاريات", "الزريات", "زريات");
    private static final List<String> TUR = Arrays.asList("الطور", "طور");
    private static final List<String> NJM = Arrays.asList("النجم", "نجم");
    private static final List<String> QMR = Arrays.asList("القمر", "قمر");
    private static final List<String> RHM = Arrays.asList("الرحمن", "رحمن", "الرحمان", "رحمان");
    private static final List<String> WQA = Arrays.asList("الواقعه", "واقعه", "الواقعة", "واقعة", "وقعه", "الوقعه", "الوقعة", "وقعة");
    private static final List<String> HDY = Arrays.asList("الحديد", "حديد");
    private static final List<String> MGD = Arrays.asList("المجادله", "مجادله", "مجدله", "المجدله");


    //59-86
    private static final List<String> HSR = Arrays.asList("الحشر", "حشر");
    private static final List<String> MUT = Arrays.asList("الممتحنه", "ممتحنه", "الممتحنة", "ممتحنة");
    private static final List<String> SAF = Arrays.asList("الصق", "صف");
    private static final List<String> JUM = Arrays.asList("الجمعه", "جمعه", "جمعة", "الجمعة");
    private static final List<String> MNF = Arrays.asList("المنافقون", "منافقون", "منفقون", "المنفقون", "المنافقين", "منافقين");
    private static final List<String> TGN = Arrays.asList("التغابن", "تغابن", "التغبن", "تغبن");
    private static final List<String> TLQ = Arrays.asList("الطلاق", "طلاق");
    private static final List<String> TRM = Arrays.asList("التحريم", "تحريم");
    private static final List<String> MLK = Arrays.asList("الملك", "ملك");
    private static final List<String> QLM = Arrays.asList("القلم", "قلم");
    private static final List<String> HQA = Arrays.asList("الحاقه", "حاقه", "الحاقة", "حاقة", "حقه", "الحقه", "الحقة", "حقة");
    private static final List<String> MRJ = Arrays.asList("المعارج", "معارج", "معرج", "المعرج");
    private static final List<String> NOH = Arrays.asList("نوح", "النوح");
    private static final List<String> JIN = Arrays.asList("الجن", "حن");
    private static final List<String> MZL = Arrays.asList("المزمل", "مزمل", "مذمل", "المذمل");
    private static final List<String> MDT = Arrays.asList("المدثر", "مدثر", "مدسر", "المدسر");
    private static final List<String> QYM = Arrays.asList("القيامه", "قيامه", "قيامة", "القيامة", "القيمه", "قيمه", "القيمة", "قيمة");
    private static final List<String> INS = Arrays.asList("الانسان", "الأنسان", "الإنسان", "انسان", "أنسان", "إنسان");
    private static final List<String> MRS = Arrays.asList("المرسلات", "مرسلات", "المرسلت", "مؤسلت");
    private static final List<String> NBA = Arrays.asList("النبأ", "نبأ", "نباء", "النباء");
    private static final List<String> NZA = Arrays.asList("النازعات", "نازعات", "نزعت", "النزعت", "ناذعات", "الناذعات");
    private static final List<String> ABS = Arrays.asList("عبس", "العبس");
    private static final List<String> TKR = Arrays.asList("التكوير", "تكوير", "تقوير", "التقوير");
    private static final List<String> INF = Arrays.asList("الانفطار", "الأنفطار", "الإنفطار", "انفطار", "أنفطار", "انفطر", "إنفطر", "أنفطر", "إنفطار");
    private static final List<String> MTF = Arrays.asList("المطففين", "مطففين");
    private static final List<String> SQA = Arrays.asList("الانشقاق", "الأنشقاق", "الإنشقاق", "اشنقاق", "أنشقاق", "انشقق", "إنشقاق");
    private static final List<String> BRG = Arrays.asList("البروج", "بروج");
    private static final List<String> TRQ = Arrays.asList("الطارق", "طارق", "طرق", "الطرق");


    //87-114
    private static final List<String> ALA = Arrays.asList("الاعلى", "الأعلى", "الإعلى", "اعلى", "أعلى","إعلى","الاعلا", "الأعلا", "الإعلا", "اعلا", "أعلا","إعلا");
    private static final List<String> GSA = Arrays.asList("الغاشيه", "الغاشية", "غاشيه", "غاشية", "غشيه", "الغشيه", "الغشية", "غشية");
    private static final List<String> FJR = Arrays.asList("الفجر", "فجر");
    private static final List<String> BLD = Arrays.asList("البلد", "بلد");
    private static final List<String> HMS = Arrays.asList("الشمس", "شمس");
    private static final List<String> LIL = Arrays.asList("الليل", "ليل");
    private static final List<String> DUH = Arrays.asList("الضحى", "ضحى");
    private static final List<String> SHR = Arrays.asList("الشرح", "شرح");
    private static final List<String> TIN = Arrays.asList("التين", "تين");
    private static final List<String> ALQ = Arrays.asList("العلق", "علق");
    private static final List<String> QDR = Arrays.asList("القدر", "قدر");
    private static final List<String> BYN = Arrays.asList("البينه", "بينه", "البينة", "بينة");
    private static final List<String> ZLA = Arrays.asList("الزلزله", "زلزله", "الزلزلة", "الذلذله", "ذلذله", "الزلزلة", "ذلذلة", "الذلذلة");
    private static final List<String> ADY = Arrays.asList("العاديات", "عاديات");
    private static final List<String> QRA = Arrays.asList("القارعه", "قارعه", "القارعة", "قارعة");
    private static final List<String> TKU = Arrays.asList("التكاثر", "تكاثر", "التكاسر", "تكاسر");
    private static final List<String> ASR = Arrays.asList("العصر", "عصر");
    private static final List<String> HMZ = Arrays.asList("الهمزه", "الهمزة", "همزه", "الهمزة", "الهمذه", "الهمذه", "همذه", "همذة");
    private static final List<String> FIL = Arrays.asList("الفيل", "فيل");
    private static final List<String> QRS = Arrays.asList("قريش", "القريش");
    private static final List<String> MAN = Arrays.asList("الماعون", "ماعون", "معون", "المعون");
    private static final List<String> KWA = Arrays.asList("الكوثر", "كوثر", "كوسر", "الكوسر");
    private static final List<String> KFR = Arrays.asList("الكافرون", "كافرون", "كفرون", "الكفرون", "الكافرين", "كافرين");
    private static final List<String> NSR = Arrays.asList("النصر", "نصر");
    private static final List<String> MSD = Arrays.asList("المسد", "مسد");
    private static final List<String> IKS = Arrays.asList("الاخلاص", "الأخلاص", "الإخلاص", "اخلاص", "أخلاص", "إخلاص");
    private static final List<String> FLQ = Arrays.asList("الفلق", "فلق");
    private static final List<String> NAS = Arrays.asList("الناس", "ناس");


    /**
     * Locations
     */
    public static final List<String> PART = Arrays.asList("الجزء", "جذء", "الجذء", "جزء");
    public static final List<String> AYA = Arrays.asList("ايه", "الايه");
    public static final List<String> HZEB = Arrays.asList("حذب", "الحذب", "حزب", "الحزب");
    public static final List<String> PAGE = Arrays.asList("الصفحة", "صفحة", "الصفحه", "الصفحه");
    public static final List<String> QUARTER = Arrays.asList("ربع", "الربع");

    /**
     * To add support arabic as locale variable for the speech recognition
     */
    public static final String LOCALE_ARABIC = "ar";

    public static final Map<QuranIndex, List<String>> Surahs;

    static {
        Map<QuranIndex, List<String>> sSurahs = new HashMap<>();


        //1-29
        sSurahs.put(QuranIndex.FTA, FTA);
        sSurahs.put(QuranIndex.BQR, BQR);
        sSurahs.put(QuranIndex.IMR, IMR);
        sSurahs.put(QuranIndex.NSA, NSA);
        sSurahs.put(QuranIndex.MDA, MDA);
        sSurahs.put(QuranIndex.ANM, ANM);
        sSurahs.put(QuranIndex.ARF, ARF);
        sSurahs.put(QuranIndex.ANF, ANF);
        sSurahs.put(QuranIndex.TWB, TWB);
        sSurahs.put(QuranIndex.YNS, YNS);
        sSurahs.put(QuranIndex.HUD, HUD);
        sSurahs.put(QuranIndex.YSF, YSF);
        sSurahs.put(QuranIndex.RAD, RAD);
        sSurahs.put(QuranIndex.IBR, IBR);
        sSurahs.put(QuranIndex.HJR, HJR);
        sSurahs.put(QuranIndex.NHL, NHL);
        sSurahs.put(QuranIndex.ISR, ISR);
        sSurahs.put(QuranIndex.KHF, KHF);
        sSurahs.put(QuranIndex.MRY, MRY);
        sSurahs.put(QuranIndex.THA, THA);
        sSurahs.put(QuranIndex.ANB, ANB);
        sSurahs.put(QuranIndex.HAJ, HAJ);
        sSurahs.put(QuranIndex.MON, MON);
        sSurahs.put(QuranIndex.NOR, NOR);
        sSurahs.put(QuranIndex.FUQ, FUQ);
        sSurahs.put(QuranIndex.SHU, SHU);
        sSurahs.put(QuranIndex.NML, NML);
        sSurahs.put(QuranIndex.QAS, QAS);
        sSurahs.put(QuranIndex.ANK, ANK);


        //30-58
        sSurahs.put(QuranIndex.ROM, ROM);
        sSurahs.put(QuranIndex.LUQ, LUQ);
        sSurahs.put(QuranIndex.SJD, SJD);
        sSurahs.put(QuranIndex.SBA, SBA);
        sSurahs.put(QuranIndex.AZB, AZB);
        sSurahs.put(QuranIndex.FTR, FTR);
        sSurahs.put(QuranIndex.YSN, YSN);
        sSurahs.put(QuranIndex.SFT, SFT);
        sSurahs.put(QuranIndex.SAD, SAD);
        sSurahs.put(QuranIndex.ZMR, ZMR);
        sSurahs.put(QuranIndex.GFR, GFR);
        sSurahs.put(QuranIndex.YSF, YSF);
        sSurahs.put(QuranIndex.FST, FST);
        sSurahs.put(QuranIndex.SRA, SRA);
        sSurahs.put(QuranIndex.ZRF, ZRF);
        sSurahs.put(QuranIndex.DKN, DKN);
        sSurahs.put(QuranIndex.GTY, GTY);
        sSurahs.put(QuranIndex.AGF, AGF);
        sSurahs.put(QuranIndex.MHD, MHD);
        sSurahs.put(QuranIndex.FTH, FTH);
        sSurahs.put(QuranIndex.JRT, JRT);
        sSurahs.put(QuranIndex.QAF, QAF);
        sSurahs.put(QuranIndex.ZRT, ZRT);
        sSurahs.put(QuranIndex.TUR, TUR);
        sSurahs.put(QuranIndex.NJM, NJM);
        sSurahs.put(QuranIndex.QMR, QMR);
        sSurahs.put(QuranIndex.RHM, RHM);
        sSurahs.put(QuranIndex.WQA, WQA);
        sSurahs.put(QuranIndex.HDY, HDY);
        sSurahs.put(QuranIndex.MGD, MGD);

        //59-86
        sSurahs.put(QuranIndex.HSR, HSR);
        sSurahs.put(QuranIndex.MUT, MUT);
        sSurahs.put(QuranIndex.SAF, SAF);
        sSurahs.put(QuranIndex.JUM, JUM);
        sSurahs.put(QuranIndex.MNF, MNF);
        sSurahs.put(QuranIndex.TGN, TGN);
        sSurahs.put(QuranIndex.TLQ, TLQ);
        sSurahs.put(QuranIndex.TRM, TRM);
        sSurahs.put(QuranIndex.MLK, MLK);
        sSurahs.put(QuranIndex.QLM, QLM);
        sSurahs.put(QuranIndex.HQA, HQA);
        sSurahs.put(QuranIndex.MRJ, MRJ);
        sSurahs.put(QuranIndex.NOH, NOH);
        sSurahs.put(QuranIndex.JIN, JIN);
        sSurahs.put(QuranIndex.MZL, MZL);
        sSurahs.put(QuranIndex.MDT, MDT);
        sSurahs.put(QuranIndex.QYM, QYM);
        sSurahs.put(QuranIndex.INS, INS);
        sSurahs.put(QuranIndex.MRS, MRS);
        sSurahs.put(QuranIndex.NBA, NBA);
        sSurahs.put(QuranIndex.NZA, NZA);
        sSurahs.put(QuranIndex.ABS, ABS);
        sSurahs.put(QuranIndex.TKR, TKR);
        sSurahs.put(QuranIndex.INF, INF);
        sSurahs.put(QuranIndex.MTF, MTF);
        sSurahs.put(QuranIndex.SQA, SQA);
        sSurahs.put(QuranIndex.BRG, BRG);
        sSurahs.put(QuranIndex.QAS, QAS);
        sSurahs.put(QuranIndex.TRQ, TRQ);


        //87-114
        sSurahs.put(QuranIndex.ALA, ALA);
        sSurahs.put(QuranIndex.GSA, GSA);
        sSurahs.put(QuranIndex.FJR, FJR);
        sSurahs.put(QuranIndex.BLD, BLD);
        sSurahs.put(QuranIndex.HMS, HMS);
        sSurahs.put(QuranIndex.LIL, LIL);
        sSurahs.put(QuranIndex.DUH, DUH);
        sSurahs.put(QuranIndex.SHR, SHR);
        sSurahs.put(QuranIndex.TIN, TIN);
        sSurahs.put(QuranIndex.ALQ, ALQ);
        sSurahs.put(QuranIndex.QDR, QDR);
        sSurahs.put(QuranIndex.BYN, BYN);
        sSurahs.put(QuranIndex.ZLA, ZLA);
        sSurahs.put(QuranIndex.ADY, ADY);
        sSurahs.put(QuranIndex.QRA, QRA);
        sSurahs.put(QuranIndex.TKU, TKU);
        sSurahs.put(QuranIndex.ASR, ASR);
        sSurahs.put(QuranIndex.HMZ, HMZ);
        sSurahs.put(QuranIndex.FIL, FIL);
        sSurahs.put(QuranIndex.QRS, QRS);
        sSurahs.put(QuranIndex.MAN, MAN);
        sSurahs.put(QuranIndex.KWA, KWA);
        sSurahs.put(QuranIndex.KFR, KFR);
        sSurahs.put(QuranIndex.NSR, NSR);
        sSurahs.put(QuranIndex.MSD, MSD);
        sSurahs.put(QuranIndex.IKS, IKS);
        sSurahs.put(QuranIndex.FLQ, FLQ);
        sSurahs.put(QuranIndex.NAS, NAS);


        Surahs = Collections.unmodifiableMap(sSurahs);
    }
}
