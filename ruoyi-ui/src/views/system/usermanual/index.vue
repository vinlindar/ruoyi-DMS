<template>
    <div>
        <iframe :src="pdfSrc" style="width: 100%; height: 100vh;" frameborder="0"></iframe>
    </div>
</template>
<script>

export default{
    data(){
        return {
            pdfSrc:'/user-manual.pdf'
        }
    }
}
</script>