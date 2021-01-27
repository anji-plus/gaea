<!--
 * @Author: zyk 
 * @Date: 2020-07-13 11:04:24 
 * @Last Modified by:   zyk 
 * @Last Modified time: 2020-07-13 11:04:24 
 !-->
<template>
  <div class="wscn-http404-container">
    <div class="wscn-http404">
      <img class="pic-404__parent" src="@/assets/404_images/404_new.png" alt="404" width="50%">
    </div>
    <div class="bullshit">
      <div class="bullshit__oops">{{ $t('promptMessage.OOPS') }}</div>
      <div class="bullshit__headline">{{ $t('promptMessage.headline') }}</div>
      <div class="bullshit__info">{{ $t('promptMessage.info') }}</div>
      <a href="" class="bullshit__return-home">{{ $t('promptMessage.goHome') }}</a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Page404',
  computed: {
    message() {
      return 'The webmaster said that you can not enter this page...'
    },
  },
}
</script>

<style lang="scss" scoped>
.wscn-http404-container {
  // padding-top:100px;
  background-color: #fff;
  border-radius: 4px;
  width: 100%;
  height: 100%;
  padding-bottom: 200px;
  display: flex;
  align-items: center;
  justify-content: center;

  .wscn-http404 {
    width: 30%;
  }
  .bullshit {
    position: relative;
    float: left;
    width: 300px;
    padding: 30px 0;
    overflow: hidden;
    &__oops {
      font-size: 32px;
      font-weight: bold;
      line-height: 40px;
      color: #1482f0;
      opacity: 0;
      margin-bottom: 20px;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-fill-mode: forwards;
    }
    &__headline {
      font-size: 20px;
      line-height: 24px;
      color: #222;
      font-weight: bold;
      opacity: 0;
      margin-bottom: 10px;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-delay: 0.1s;
      animation-fill-mode: forwards;
    }
    &__info {
      font-size: 13px;
      line-height: 21px;
      color: grey;
      opacity: 0;
      margin-bottom: 30px;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-delay: 0.2s;
      animation-fill-mode: forwards;
    }
    &__return-home {
      display: block;
      float: left;
      width: 110px;
      height: 36px;
      background: #1482f0;
      border-radius: 100px;
      text-align: center;
      color: #ffffff;
      opacity: 0;
      font-size: 14px;
      line-height: 36px;
      cursor: pointer;
      animation-name: slideUp;
      animation-duration: 0.5s;
      animation-delay: 0.3s;
      animation-fill-mode: forwards;
    }
    @keyframes slideUp {
      0% {
        transform: translateY(60px);
        opacity: 0;
      }
      100% {
        transform: translateY(0);
        opacity: 1;
      }
    }
  }
}
</style>
