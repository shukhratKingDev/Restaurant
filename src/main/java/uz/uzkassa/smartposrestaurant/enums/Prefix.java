package uz.uzkassa.smartposrestaurant.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import uz.uzkassa.smartposrestaurant.utils.SecurityUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 11.25.2022 10:20
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Prefix implements Serializable {

    ORDER{
        public String getName(LanguageEnum language){
            return "ЗA";
        }
    };
    private final String name;
    private final String code;

    Prefix(){
        this.code=this.name();
        this.name=this.getName();
    }
    public abstract  String getName(LanguageEnum language);
    public String getName(){
        return getName(SecurityUtils.getCurrentRequestLanguageEnum());
    }
    @JsonCreator
    @SuppressWarnings("unused")
    public static Prefix forValue(Map<String,String> value){
        return Prefix.valueOf(value.get("code"));
    }
}
