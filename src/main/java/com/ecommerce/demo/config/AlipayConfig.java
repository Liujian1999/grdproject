package com.ecommerce.demo.config;

public class AlipayConfig {
    // 作为身份标识的应用ID
    public static String app_id = "2021000117607295";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key  = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCHEod8tl+8bnAQ72HMC3fEUlHSYsUYAWsxzidraj2jEsciWJDY9+18tref8hiGVXx8NCJ0rDK5UYofLA9wCY6B7kYJFfxf3zPHBQJNpCIHR8ifs/PEZAwLoOYOrX+Piq+PwG09n7dAGSqyFENIUecVERJaiES2AHr++G1mINkDBt+pqZJxVPwWRe+5XL8n4invll6pJTQ1ctDv7q+vgS93Rrlofs/0B1g/9XAZYk92LOGjt6wUNQiY9TkFurY4Zkm6xWe0LwNIHymb68zatePfYDcvAnTdzB7MVHSJ3Obaw4yjt0dPkF1wLa4r0CLYIM0qEY9jNQYHrqFYmBo77C6FAgMBAAECggEACC6lKnbZwc5m13/IytV/AdOCxV3aVqPvXGsClE0xQqmminwW+i26oy1mXjgKKEkRa2y1EaBEylkYJt9CaCUMDu7iKza7vQsGVe4Fv7JLpWlBy/rYr3LoD+RFS2pTKdza2bhsZalBLo7XuUXk9YKQnwQTuow6rVPGH5/o8KDYxIgUzDgHqp3R5T9H/SuFBI0R7WG3oYZ1InBjjsiSKVbO6J/3if2X2VcoDtdOTGDllAXkCfPPYDBM53S5Mv0lEkEP7gd5u1KuJYfc/FSAJtGTo2dS4+lm9cy3S3ase3a+tWiVamxgrB8IbBjYnhMJadQ9FX95tSjeVVi/3SUyanyxAQKBgQDwAO3okiHpC16W0/JMSr+PJKCuwLZVdqY1nNXOoom617ToeF7VxiFxoiG9fIFNXW+uCXMb/RwMJ5lBVkif4cet2qQgffEOIv6G//54sGsIOeYQ9buxI+t0LPuSlh7s/wykBIUbEgOmSm3SyDUMBAVXNLO3hUpmMecd/uOq1Iz25QKBgQCQEzTmcd5PVJja/rmfWltRxKnKAheESA0XL8B5wPE/yIZT161V8ZsCeT3DqCanPo+LDXIArcCStJNAkgTZHh+QdL0WBhxU0zuOWUi8SSITKwZyXRxHMfeNVdaADp37I1ZCUXEqyiSakqUdXGfsFaUjkLxtbYyCzbarVkiEkyY/IQKBgQDWyU4g1B7q69TkEJwIMOEMXtA6dJb5Fy0aT9h8i7PM9dyeK5Nbp2wowNuHM9SYAyW+UBCnHLasHggmk+x8wVetPD2kInHHoAO/l3wKUIAJJZ/Cz9gAaM//8X3XKuhUWtDaPH1Ow6lmebedarUmoraQ28Z2WCBEIybeDk17har+DQKBgQCJYYcj5phxVN2clSAzD1nckobElVIPVRUGcwY6Evz4MSHFePdT6FpC9ZErrH+PIlyF34BBnWzvdziibZop3kuC144mHhWN3Q4BIqn0wFe5p2EyVA7O5JqFLsCmw0lpW7y46CfdAXv92K+RW7fMqqcZTr4eKkO9xqLUpiXoJNhngQKBgANxxshR5QyGuzNLT/23Zx2O/Vcaz4UiliFsgdiWWanxP4kZy2FfXGTpMMFG/F+QifBUjlfi5QDKmQJTJNs+2OwFvt648FcE86Vgh7gDggjBcwDt4GI9cg9JmY1TfUX1HTFR5DQqs6v70URjjQubApyeCF/KOe1phQCgLQatkROI";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr1PKFNrK3O18JZQuZvt9Kkm9YwGbKcIbhQPlaQM1pZPt2rnjkCDEjAQ1AvkLgzQiNcvpc01LbQS3qTDOZqdOEDcEZjMYBS/x5x9mljiHjz50hr5iE9qWsLRfotw9RsOMiI83V7AHhwKG1ICfejy+jyHduzo9xOQUCs6J0TX1FY8hQbn5j+Lz6OufRXfCmCTRIyQb5s1A5uZnBtoMMjHCQlmsaaBI2+MIeOCAOd8B3HF+K498dTtoF8/E0gMLxodhAGYxiQ0TLD+aPAbEcHSJRmSxPUBFdh6AZDJVBOWcBRl/UO9E5qsuNZfdDLGPcdn39risBpwehPvQBbMBZm5DxwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://www.baidu.com";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://www.baidu.com";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}
