package com.example.myapplication.ui.schedule;



    public class utils {

        public static int getDay(String day) {
            int j = 0;
            switch (day) {
                case "周一":{
                    j = 1;
                    break;
                }
                case "周二":{
                    j = 2;
                    break;
                }
                case "周三":{
                    j = 3;
                    break;
                }
                case "周四":{
                    j = 4;
                    break;
                }
                case "周五":{
                    j = 5;
                    break;
                }
                case "周六":{
                    j = 6;
                    break;
                }
                case "周日":{
                    j = 7;
                    break;
                }
            }
            return j;
        }



        //////////

        public static int getweek(String day) {
            int j = 0;
            switch (day) {
                case "第一周":{j = 1;break;}
                case "第二周":{j = 2;break;}
                case "第三周":{j = 3;break;}
                case "第四周":{j = 4;break;}
                case "第五周":{j = 5;break;}
                case "第六周":{j = 6;break;}
                case "第七周":{j = 7;break;}
                case "第八周":{j = 8;break;}
                case "第九周":{j = 9;break;}
                case "第十周":{j = 10;break;}
                case "第十一周":{j = 11;break;}
                case "第十二周":{j = 12;break;}
                case "第十三周":{j = 13;break;}
                case "第十四周":{j = 14;break;}
                case "第十五周":{j = 15;break;}
                case "第十六周":{j = 16;break;}
                case "第十七周":{j = 17;break;}
                case "第十八周":{j = 18;break;}
                case "第十九周":{j = 19;break;}
                case "第二十周":{j = 20;break;}


            }
            return j;
        }

        ////////////


        public static int gettime(String day) {
            int j = 0;
            switch (day) {
                case "第1节":{
                    j = 1;
                    break;
                }
                case "第2节":{
                    j = 2;
                    break;
                }
                case "第3节":{
                    j = 3;
                    break;
                }
                case "第4节":{
                    j = 4;
                    break;
                }
                case "第5节":{
                    j = 5;
                    break;
                }
                case "第6节":{
                    j = 6;
                    break;
                }
                case "第7节":{
                    j = 7;
                    break;
                }
                case "第8节":{
                    j = 8;
                    break;
                }
                case "第9节":{
                    j = 9;
                    break;
                }
                case "第10节":{
                    j = 10;
                    break;
                }
                case "第11节":{
                    j = 11;
                    break;
                }
                case "第12节":{
                    j = 12;
                    break;
                }
                case "第13节":{
                    j = 13;
                    break;
                }

            }
            return j;
        }

    }


