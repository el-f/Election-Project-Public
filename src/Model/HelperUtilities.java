package Model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HelperUtilities {

    public static final double FAIR_CHANCE = 0.5;
    public static final double HIGH_CHANCE = 0.8;
    public static final int SOLDIER_MIN_YEAR = LocalDate.now().getYear() - Soldier.MAX_AGE;
    public static final int SOLDIER_MAX_YEAR = LocalDate.now().getYear() - Soldier.MIN_AGE;
    public static final int CITIZEN_MIN_YEAR = LocalDate.now().getYear() - 120;
    public static final int CITIZEN_MAX_YEAR = SOLDIER_MIN_YEAR;


    public static <T> T getRandom(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static String capitalizeFirstLetter(String str) {
        return str.toUpperCase().charAt(0) + str.substring(1).toLowerCase();
    }

    public static String getRandomName() {
        return getRandom(defaultNames);
    }

    public static int getRandomYearForCitizen() {
        return getRandomInt(CITIZEN_MIN_YEAR, CITIZEN_MAX_YEAR);
    }

    public static int getRandomYearForSoldier() {
        return getRandomInt(SOLDIER_MIN_YEAR, SOLDIER_MAX_YEAR);
    }

    public static int getRandomID() {
        return getRandomInt(111111111, 999999999);
    }

    public static String getRandomAddress() {
        return getRandom(defaultAddresses);
    }

    public static String getRandomPartyName() {
        return getRandom(defaultParties);
    }

    public static boolean getRandomBoolean() {
        return Math.random() <= FAIR_CHANCE;
    }

    public static boolean getHighChanceBoolean() {
        return Math.random() <= HIGH_CHANCE;
    }

    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public final static List<String> defaultParties = Arrays.asList("Likud", "Meretz", "Avoda", "Kahol Lavan",
            "Shas", "Gimel", "Reshima Meshutefet", "Israel Beiteinu", "Gesher", "Yamina", "Yesh Atid", "Derech Eretz",
            "The Pirates");

    public final static List<String> defaultAddresses = Arrays.asList("Afula", "Akko", "Arad", "Ashdod",
            "Ashqelon", "Bat Yam", "Beersheba", "Bet Shean", "Beit Shemesh", "Bnei Brak", "Caesarea", "Dimona", "Dor",
            "Eilat", "Elad", "Givat Shmuel", "Givatayim", "Hadera", "Haifa", "Herzliyya", "Hod-HaSharon", "Holon",
            "Jerusalem", "Karmiel", "Kfar Saba", "Kfar Yona", "Kiryat-Ata", "Kiryat Bialik", "Kiryat-Gat",
            "Kiryat Malakhi", "Kiryat Motzkin", "Kiryat Ono", "Kiryat Yam", "Lod", "Ma'alot-Tarshiha", "Meron",
            "Migdal HaEmek", "Modi'in", "Nahariyya", "Nazareth", "Nesher", "Ness-Ziona", "Netanya", "Netivot",
            "Nof-HaGalil", "Ofakim", "Or-Akiva", "Or-Yehuda", "Petah Tikva", "Qiryat Shemona", "Ra'anana",
            "Ramat-Gan", "Ramat-HaSharon", "Ramla", "Rehovot", "Rishon Lezion", "Rosh HaAyin", "Sderot", "Tel-Aviv",
            "Yafo", "Tiberias", "Tirat-Carmel", "Yavne", "Yehud-Monosson", "Yokneam-Illit", "Zefat");

    public final static List<String> defaultNames = Arrays.asList("Abaddon", "Alchemist", "Ancient-Apparition",
            "Anti-Mage", "ArcWarden", "Axe", "Bane", "Batrider", "Beastmaster", "Bloodseeker", "Bounty-Hunter",
            "Brewmaster", "Bristleback", "Broodmother", "Centaur", "Warrunner", "Chaos",
            "Knight", "Chen", "Clinkz", "Clockwerk", "Crystal-Maiden", "Dark-Seer", "Dark-Willow", "Dazzle",
            "Death-Prophet", "Disruptor", "Doom", "Dragon-Knight", "Drow-Ranger", "Earth-Spirit", "Earthshaker",
            "Elder-Titan", "Ember-Spirit", "Enchantress",
            "Enigma", "Faceless-Void", "Grimstroke", "Gyrocopter", "Huskar", "Invoker", "Io", "Jakiro",
            "Juggernaut", "Keeper-of-the-Light", "Kunkka", "Legion-Commander", "Leshrac", "Lich",
            "Lifestealer", "Lina", "Lion", "Lone-Druid", "Luna", "Lycan", "Magnus", "Mars", "Medusa", "Meepo",
            "Mirana", "Monkey-King", "Morphling", "Naga-Siren", "Nature's-Prophet", "Necrophos",
            "Night-Stalker", "Nyx_Assassin", "Ogre_Magi", "Omniknight", "Oracle", "Outworld-Devourer",
            "Pangolier", "Phantom-Assassin", "Phantom-Lancer", "Phoenix", "Puck", "Pudge", "Pugna",
            "Queen-of-Pain", "Razor", "Riki", "Rubick", "Sand-King", "Shadow-Demon", "Shadow-Fiend",
            "Shadow-Shaman", "Silencer", "Skywrath-Mage", "Slardar", "Slark", "Snapfire",
            "Sniper", "Spectre", "Spirit-Breaker", "Storm-Spirit", "Sven", "Techies", "Templar-Assassin",
            "Terrorblade", "Tidehunter", "Timbersaw", "Tinker", "Tiny", "Treant-Protector",
            "Troll-Warlord", "Tusk", "Underlord", "Undying", "Ursa", "Vengeful-Spirit", "Venomancer",
            "Viper", "Visage", "Void-Spirit", "Warlock", "Weaver", "Windranger", "Winter", "Wyvern",
            "Witch-Doctor", "Wraith-King", "Zeus", "Adam", "Adi", "Adiel", "Adir", "Aharon", "Akiva", "Alon",
            "Ami", "Amihay", "Amikam", "Amir", "Amir", "Amiram", "Amit", "Amitay", "Amnon", "Amos", "Amram", "Ariel",
            "Arik", "Arye", "Asaf", "Asher", "Asi", "Avi", "Avihay", "Aviel", "Avigdor", "Avihu", "Avinoam", "Aviram",
            "Aviv", "Avner", "Avraham", "Avshalom", "Azriel", "Barak", "Barukh", "Beni", "Bentzi", "Bentzion",
            "Betzalel", "Binyamin", "Boaz", "Dan", "Dani", "Daniel", "David", "Dekel", "Dodik", "Doron", "Dov",
            "Dror", "Dubi", "Dudi", "Dudu", "Dvir", "Edan", "Eden", "Efi", "Efraim", "Egoz", "Ehud", "Eitan",
            "Elazar", "Eldad", "Elhanan", "Eli", "Eliezer", "Elimelekh", "Elisha", "Eliyahu", "Elkana", "Elyakim",
            "Elyashiv", "Eran", "Erez", "Eyal", "Ezer", "Ezra", "Gabi", "Gabriel", "Gad", "Gadi", "Gal", "Gay",
            "Gedalya", "Gefen", "Gershon", "Gidi", "Gidon", "Gil", "Gilad", "Gili", "Giora", "Golan", "Hagay",
            "Hanan", "Hananel", "Hananya", "Hanokh", "Harel", "Hayim", "Hezi", "Hizkiyahu", "Idan", "Ido",
            "Igal", "Ilan", "Immanuel", "Ishay", "Israel", "Issahar", "Itamar", "Itay", "Ito", "Itzhak",
            "Itzhar", "Itzik", "Katzir", "Kobi", "Levi", "Lior", "Liran", "Liron", "Malakhi", "Malkiel",
            "Maor", "Maoz", "Matan", "Mati", "Matityahu", "Meir", "Menahem", "Menashe", "Meni", "Meshulam", "Mikha",
            "Mikhael", "Moni", "Mordekhay", "Moshe", "Moti", "Nadav", "Naftali", "Nahman", "Nahshon", "Nahum", "Naor",
            "Narkis", "Natan", "Nati", "Neeman", "Nehemya", "Nerya", "Netanel", "Nir", "Nisan", "Nisim", "Nitzan",
            "Noah", "Noam", "Nuriel", "Oded", "Ofer", "Ofir", "Ohad", "Omer", "Omri", "Or", "Oren", "Ori", "Oron",
            "Osher", "Ovadya", "Oz", "Peretz", "Pesah", "Pinhas", "Pini", "Raanan", "Rafael", "Rafi", "Rahamim",
            "Rahmiel", "Ram", "Rami", "Ran", "Rani", "Raz", "Razi", "Raziel", "Rehavam", "Reuven", "Roi", "Ron",
            "Ronen", "Roni", "Rotem", "Rubi", "Sami", "Sason", "Sefi", "Shabtay", "Shahar", "Shalom", "Shamay",
            "Sharon", "Shaul", "Shay", "Shaya", "Shealtiel", "Shevah", "Shimi", "Shimon", "Shimshon", "Shlomi",
            "Shlomo", "Shmaryahu", "Shmuel", "Shmulik", "Shraga", "Shuki", "Simha", "Tal", "Tamir", "Tamir",
            "Tomer", "Tomi", "Tuvya", "Tzadok", "Tzahi", "Tzfanya", "Tzvi", "Tzvika", "Uri", "Uriel", "Uzi",
            "Uziel", "Yaakov", "Yair", "Yakir", "Yanay", "Yaniv", "Yarden", "Yaron", "Yedidya", "Yehezkel",
            "Yehiel", "Yehoshua", "Yehuda", "Yekutiel", "Yerahmiel", "Yermiyahu", "Yeshayahu", "Yoav", "Yoel",
            "Yohanan", "Yohay", "Yona", "Yonatan", "Yoni", "Yosef", "Yosi", "Yotam", "Yovel", "Yuval", "Zakharya",
            "Zeev", "Zevulon", "Zion", "Ziv", "Zohar", "Ada", "Adi", "Adina", "Ahuva", "Alisa", "Alona", "Aluma",
            "Amira", "Anat", "Ariela", "Avigail", "Avital", "Aviva", "Avivit", "Aviya", "Ayala", "Ayelet", "Batsheva",
            "Batya", "Bilha", "Bina", "Brakha", "Brurya", "Dafna", "Daliya", "Dana", "Daniela", "Dasi", "Dikla",
            "Dina", "Ditza", "Dorit", "Drora", "Dvora", "Eden", "Edna", "Efrat", "Eilat", "Einat", "Einav",
            "Elisheva", "Emuna", "Ester", "Esti", "Eti", "Gabi", "Gabriela", "Gali", "Galit", "Galiya",
            "Geula", "Gila", "Hadar", "Hadas", "Hadasa", "Hagar", "Hagit", "Hamutal", "Hana", "Hava", "Havatzelet",
            "Haviva", "Haya", "Hedva", "Hemda", "Hen", "Hila", "Ilana", "Ilanit", "Iris", "Irit", "Israela", "Kalanit",
            "Karmela", "Karmit", "Keren", "Kineret", "Kohava", "Lea", "Levana", "Lilah", "Limor", "Liora", "Liran",
            "Liron", "Livnat", "Mali", "Malka", "Margalit", "Masua", "Maya", "Mazal", "Meira", "Meyrav", "Meytal",
            "Menora", "Menuha", "Mikhal", "Mira", "Miri", "Miryam", "Mor", "Moran", "Moriya", "Naama", "Naomi",
            "Nava", "Nehama", "Neta", "Netali", "Nili", "Nira", "Nirit", "Nitza", "Nitzana", "Noa", "Nofar",
            "Noga", "Noy", "Nurit", "Ofira", "Ofra", "Ora", "Oranit", "Orit", "Orli", "Orna", "Osnat", "Pnina",
            "Rahel", "Rakefet", "Reut", "Revital", "Riki", "Rina", "Rivka", "Roni", "Ronit", "Rotem", "Ruhama",
            "Rut", "Ruti", "Sapir", "Sara", "Sarit", "Savyon", "Shahar", "Shani", "Shanit", "Sharon", "Shifra",
            "Shir", "Shira", "Shirel", "Shiri", "Shirli", "Shlomit", "Shosh", "Shoshana", "Shoshi", "Shula",
            "Shulamit", "Shuli", "Sigal", "Sigalit", "Sigi", "Simha", "Sivan", "Smadar", "Stav", "Tair", "Tali",
            "Talya", "Tamar", "Tami", "Teena", "Tehila", "Thiya", "Tiki", "Tikva", "Tirtza", "Tova", "Tzameret",
            "Tzila", "Tzili", "Tzipi", "Tzipora", "Tzviya", "Varda", "Vardina", "Vardit", "Vered", "Yaara", "Yael",
            "Yafa", "Yamit", "Yardena", "Yasmin", "Yehudit", "Yemima", "Yifat", "Yoha", "Yoheved", "Yohi", "Yona",
            "Yonit", "Yuli", "Zahava", "Ziona", "Ziva");


}
