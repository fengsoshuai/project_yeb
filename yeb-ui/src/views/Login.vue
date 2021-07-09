<template>
  <div>
    <el-form v-loading="loading"
             element-loading-text="正在登陆..."
             element-loading-spinner="element-loading-spinner"
             element-loading-background="element-loading-background"
             :rules="rules" ref="loginForm" :model="loginForm" class="loginContainer">
      <h3 class="loginTitle">系统登陆</h3>
      <el-form-item prop="username">
        <el-input type="text" auto-complete="false" v-model="loginForm.username" placeholder="请输入登陆名"/>
      </el-form-item>
      <el-form-item prop="password" style="margin-bottom: 10px">
        <el-input type="password" auto-complete="false" v-model="loginForm.password" placeholder="请输入密码"/>
      </el-form-item>
      <el-form-item prop="code">
        <el-input size="normal" class="loginCaptcha" type="text" auto-complete="false" v-model="loginForm.code" placeholder="点击图片更换验证码"/>
        <img class="loginCodeImg" :src="captchaUrl" alt="" @click="updateCaptchaUrl">
      </el-form-item>

      <el-button type="primary" style="width: 100%" @click="submitLoginForm">登陆</el-button>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      captchaUrl: '/captcha?time=' + new Date() ,
      loginForm : {
        username : 'admin',
        password : '123456',
        code : ''
      },
      rules: {
        username: [
          { required: true, message: '请输入登陆名', trigger: 'blur' },
          { min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '长度6位以上', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '验证码不能为空', trigger: 'blur' }
        ],
      },
      loading: false
    }
  },
  methods: {
    // 登陆，提交表单
    submitLoginForm() {
      this.$refs.loginForm.validate((valid) => {
        if(valid){
          this.loading = true
          this.postRequest('/login', this.loginForm).then(resp => {
            this.loading = false
            if(resp){
              // 存储用户token
              const tokenStr = resp.object.tokenHead + ' ' + resp.object.token;
              window.sessionStorage.setItem('tokenStr', tokenStr);
              this.$router.replace('/home');
            }
          })
        } else {
          this.$message.error('请填写登陆信息！');
          return false
        }
      })
    },
    updateCaptchaUrl(){
      this.captchaUrl = '/captcha?time=' + new Date()
    }
  }
}
</script>

<style scoped>
  .loginContainer {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 15px 35px;
    background: #fff;
    border: 1px solid #ecc9c9;
    box-shadow: 0 0 25px #ecc9c9;
  }

  .loginTitle {
    margin: 0 auto 40px auto;
    text-align: center;
  }

  .loginCaptcha {
    width: 160px;
    margin-right: 5px;
  }

  .loginCodeImg{
    height: 40px;
    position: relative;
    top: 15px;
    border: 1px solid green;
    box-shadow: 1px 2px 2px green;
    cursor: pointer;
  }

  .el-form-item__content {
    display: flex;
    align-items: center;
  }
</style>