<template>
    <div class="sysinfo">
        <div class="content">
            <p ><strong>开发商：怀柔实验室风电机组团队</strong></p>
            <p >版本：3.0.0</p>
            <p >设计师：程永权</p>
            <p >开发人员：黄亚振、张成林、郭翼泽</p>
            <p >管理员：黄亚振</p>
        </div>
        <div class="content2">
            <el-image
            style="height: 100%;margin-top: 50px;"
            :src="url"
            :fit="fit">
            </el-image>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
        return {
            fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
            url: '/sysinfo.jpg'
        }
        }
    }
</script>
<style>
    .sysinfo{
        display: flex;
        padding: 50px;
    }
    .title{
        font-size:30px;
    }
    .divider{
    }
    .content{
/*         box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1); */
        width: 50%;
    }
    .content2{
        height: 700px;
    }
    .content p{
        font-size:18px;
        margin-top: 100px;
        margin-left: 50px;
    }
</style>
