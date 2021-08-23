package com.zpmc.ztos.infra.base.business.dataobject;

import com.zpmc.ztos.infra.base.business.interfaces.IEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 抽取公共的字段
 *
 * @Author Java002
 * @Date 2021/6/17 13:45
 */
@Data
@MappedSuperclass
public abstract class CommonDO implements IEntity, Serializable {

    /**
     * 主键Key  避免lombok的bug，所以gkey使用全小写
     */
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键生成策略
    @Column(name = "gkey")//数据库字段名
    private Long gkey;

    @Column(name = "created")//数据库字段名
    private Date created;

    @Column(name = "creator")//数据库字段名
    private String creator;

    @Column(name = "changed")//数据库字段名
    private Date changed;

    @Column(name = "changer")//数据库字段名
    private String changer;


    //    public static Timestamp stringToTimestamp(String dateStr) {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar cal = Calendar.getInstance();
//        try {
//            Date date = sdf.parse(dateStr);
//            cal.setTime(date);
//            return new Timestamp(cal.getTimeInMillis());
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            cal.setTime(new Date());
//            return new Timestamp(cal.getTimeInMillis());
//        }
//    }
//
    public CommonDO() {
    }

    public CommonDO(Long gkey, Date created, String creator, Date changed, String changer) {
        this.gkey = gkey;
        this.created = created;
        this.creator = creator;
        this.changed = changed;
        this.changer = changer;
    }
}
