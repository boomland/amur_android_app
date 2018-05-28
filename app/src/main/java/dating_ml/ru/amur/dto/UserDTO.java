package dating_ml.ru.amur.dto;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;

import dating_ml.ru.amur.R;

public class UserDTO implements Parcelable {
    //TODO сделать private
    public String name;
    public String userAvatar;
    public String url;

    private String workplace;
    private String placeOfStudy;
    private int dist;
    private String birthDate;
    private String tinderId;
    private int gender;
    private String bio;
    private ArrayList<String> photoUrls;

    public UserDTO() {
    }

    public UserDTO(String name, String userAvatar, String url) {
        this.name = name;
        this.userAvatar = userAvatar;
        this.url = url;
    }

    public UserDTO(String name, String userAvatar, String url, int dist, Date birthDate, String tinderId,
                   int gender, String bio, ArrayList<String> photoUrls) {
        this.name = name;
        this.userAvatar = userAvatar;
        this.url = url;


        this.dist = dist;
        this.birthDate = birthDate.toString();
        this.tinderId = tinderId;
        this.gender = gender;
        this.bio = bio;
        this.photoUrls = photoUrls;
    }

    public String getName() {
        return name;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public String getUrl() {
        return url;
    }

    public int getDist() {
        return dist;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getTinderId() {
        return tinderId;
    }

    public int getGender() {
        return gender;
    }

    public String getBio() {
        return bio;
    }

    public String getWorkplace() {
        return workplace;
    }

    public ArrayList<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate.toString();
    }

    public void setTinderId(String tinderId) {
        this.tinderId = tinderId;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public void setPhotoUrls(ArrayList<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public String getPlaceOfStudy() {
        return placeOfStudy;
    }

    public void setPlaceOfStudy(String placeOfStudy) {
        this.placeOfStudy = placeOfStudy;
    }

    public long getAge() {
        return 21;
    }

    public static final UserDTO getMockUserDTO() {
        UserDTO mockUser = new UserDTO();

        mockUser.setName("Алена");

        mockUser.setBirthDate(new Date());

        mockUser.setPlaceOfStudy("SPB university of architecture");
        mockUser.setDist(2);
        mockUser.setWorkplace("Вуышптук d компании Desing studio of SPBSUACE");
        mockUser.setBio("Взгляните на карту Юга США. Штаты Алабама, Джорджия, Южная Каролина. Внизу — Флорида. «О, Флорида!», то есть цветущая, утопающая в цветах, — вскричал, по преданию, Колумб; слева — Новый Орлеан, куда, если верить литературе, сослали Манон Леско; справа, на побережье — Саванна, где умер пират Флинт — «умер в Саванне от рома» — и кричал «Пиастры! Пиастры!» его жуткий попугай. Вот отсюда и пришла Скарлетт ОХара, героиня этой книги, покорительница Америки. В американской литературе XX века нет более живого характера. Проблемы, неразрешенные комплексы, имена — это пожалуйста; но чтобы был человек, который перешагнул за обложку книги и пошел по стране, заставляя трепетать за свою судьбу, — второго такого не отыскать. Тем более что захватывает она неизвестно чем; буквально, по словам английской песни: «если ирландские глаза улыбаются, о, они крадут ваше сердце». Ретт, ее партнер, выражался, может быть, еще точнее: «то были глаза кошки во тьме» — перед прыжком, можно было бы добавить, который она совершала всегда безошибочно. Книга, в которой она явилась, оказалась тоже непонятно чем притягивающей читателя. То ли это история любви, которой нет подобия — любовь-война, любовь-истребление, — где она растет сквозь цинизм, несмотря на вытравливание с обеих сторон; то ли дамский роман, поднявшийся до настоящей литературы, потому что только дама, наверное, могла подсмотреть за своей героиней, как та целует себя в зеркале, множество других более тонких внутренних подробностей; то ли это усадебный роман, как у нас когда-то, только усадьба эта трещит, горит и исчезает в первой половине романа, будто ее и не было… По знакомым признакам не угадаешь. Да и сама писательница мало похожа на то, что мы привыкли видеть в Америке. Она, например, не признавала священное паблисити, то есть блеск известности и сыплющиеся оттуда деньги. Она отказалась снять о себе фильм — фильм! — не соглашалась на интервью, на рекламные употребления романа — мыло «Скарлетт» или мужской несессер «Ретт», особо огорчив одну исполнительницу стриптиза, которая требовала назвать свой номер «Унесенные ветром» (подразумевая, видимо, одежды); не позволила сделать из романа мюзикл. Она вступила в непримиримые отношения с кланом, определявшим литературные ранги Америки. Никому не известная домашняя хозяйка написала книгу, о которой спорили знатоки, возможно ли ее написать, и сошлись, что невозможно. Комбинат из профессоров, издателей, авторитетных критиков, давно предложивший литераторам иное: создавать имя, уступая место друг другу, но и гарантируя каждому положение в истерии литературы, творимой на глазах соединенным ударом массовых средств, — этот комбинат, получив вдруг в бестселлеры не очередного кандидата в историю, а литературу, способную зажигать умы и жить в них независимо от мнений, ее не принял. Мнение его выразил критик-законодатель Де Вото: «Значительно число читателей этой книги, но не она сама». Напрасно урезонивал своих коллег посетивший США Герберт Уэллс. «Боюсь, что эта книга написана лучше, чем иная уважаемая классика». — Голос большого писателя утонул в раздражении профессионалов. Как водится, пошли слухи. Рассказывали, что она списала книгу с дневника своей бабушки, что она заплатила Синклеру Льюису, чтобы тот написал роман… В самом составе литературы она поддержала то, что считалось примитивным и будто бы преодоленным: чистоту образа, жизнь. Ее девический дневник, полный сомнений в призвании, обнаруживает удивительную зрелость: «Есть писатели и писатели. Истинным писателем рождаются, а не делаются. Писатели по рождению создают своими образами реальных живых людей, в то время как «сделанные» — предлагают набивные чучела, танцующие на веревочках; вот почему я знаю, что я «сделанный писатель»… Позднее в письме другу она высказалась так: «… если история, которую хочешь рассказать, и характеры не выдерживают простоты, что называется, голой прозы, лучше их оставить. Видит бог, я не стилист и не могла бы им быть, если бы и хотела». Но это было как раз то, в чем у интеллектуальных кругов искать сочувствия было трудно. Молодая американская культура не выдерживала напора модных течений и наук; в литературе начали диктовать свои условия экспериментаторы, авторитеты психоанализа сошли за великих мыслителей и т.д. Доказывать в этой среде, что простая история сама по себе имеет смысл, и более глубокий, чем набор претенциозных суждений, было почти так же бесполезно, как когда-то объяснять на островах, что стеклянные бусы хуже жемчужин. Здесь требовались, по выражению Де Вото, «философские обертоны». И через сорок лет на родине Митчелл, в Джорджии, критик Флойд Уоткинс, зачисляя ее в «вульгарную литературу», осуждает этот «простой рассказ о событиях» без «философских размышлений»; тот факт, что, как сказала Митчелл, «в моем романе всего четыре ругательства и одно грязное слово», кажется ему фарисейством и отсталостью; ему не нравится ее популярность. «Великая литература может быть иногда популярной, а популярная — великой. Но за немногими известными исключениями, такими, как Библия, а не «Унесенные ветром», величие и популярность скорее противостоят друг другу, чем находятся в союзе». Остается лишь поместить в исключения Сервантеса и Данте, Рабле, Толстого, Чехова, Диккенса, Марка Твена… кого еще? В исключения из американской литературы Маргарет Митчелл так или иначе попадала.");

        ArrayList<String> photoUrls = new ArrayList<String>();
        photoUrls.add("http://cdn.wall-pix.net/cache/people-girls/ea/eac4926ec660d07b01c785bd87ab1ca08d6f55d9.jpg");
        photoUrls.add("http://luxfon.com/large/201212/18971.jpg");
        photoUrls.add("https://404store.com/2017/05/02/girls-in-hot-pants-51.jpg");
        photoUrls.add("https://valencia-gid.com/wp-content/uploads/2014/11/girl-smile-look-photo-hd-wallpaper-1-1024x640.jpg");

        mockUser.setPhotoUrls(photoUrls);

        return mockUser;
    }

    @Override
    public String toString() {
        String res = "";
        res += "touristSpot: {";
        res += "name: " + name + ", ";
        res += "userAvatar: " + userAvatar + ", ";
        res += "url: " + url + ", ";
        res += "dist: " + dist + ", ";
        res += "birthDate: " + birthDate + ", ";
        res += "tinderId: " + tinderId + ", ";
        res += "gender: " + gender + ", ";
        res += "bio: " + bio + ", ";
        res += "bio: " + bio + ", ";
        res += "photoUrls: " + photoUrls.toString() + ", ";

        res += "workplace: " + workplace + ",";
        res += "placeOfStudy: " + placeOfStudy + ",";
        res += "}";

        return res;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.userAvatar);
        dest.writeString(this.url);
        dest.writeInt(this.dist);
        dest.writeString(this.birthDate);
        dest.writeString(this.tinderId);
        dest.writeInt(this.gender);
        dest.writeString(this.bio);
        dest.writeStringList(this.photoUrls);

        dest.writeString(this.workplace);
        dest.writeString(this.placeOfStudy);
    }

    protected UserDTO(Parcel in) {
        this.name = in.readString();
        this.userAvatar = in.readString();
        this.url = in.readString();
        this.dist = in.readInt();
        this.birthDate = in.readString();
        this.tinderId = in.readString();
        this.gender = in.readInt();
        this.bio = in.readString();
        this.photoUrls = in.createStringArrayList();

        this.workplace = in.readString();
        this.placeOfStudy = in.readString();
    }

    public static final Parcelable.Creator<dating_ml.ru.amur.dto.UserDTO> CREATOR = new Parcelable.Creator<dating_ml.ru.amur.dto.UserDTO>() {
        @Override
        public dating_ml.ru.amur.dto.UserDTO createFromParcel(Parcel source) {
            return new dating_ml.ru.amur.dto.UserDTO(source);
        }

        @Override
        public dating_ml.ru.amur.dto.UserDTO[] newArray(int size) {
            return new dating_ml.ru.amur.dto.UserDTO[size];
        }
    };
}
