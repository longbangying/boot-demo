package com.xbang.bootdemo.dao.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xbang
 * @since 2019-09-12
 */
public class TMybatisplusLockTest extends Model<TMybatisplusLockTest> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 测试目标值
     */
    @TableField("target_value")
    private BigDecimal targetValue;

    /**
     * 版本号
     */
    @TableField("target_version")
    @Version
    private Long targetVersion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(BigDecimal targetValue) {
        this.targetValue = targetValue;
    }

    public Long getTargetVersion() {
        return targetVersion;
    }

    public void setTargetVersion(Long targetVersion) {
        this.targetVersion = targetVersion;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TMybatisplusLockTest{" +
        "id=" + id +
        ", targetValue=" + targetValue +
        ", targetVersion=" + targetVersion +
        "}";
    }
}
