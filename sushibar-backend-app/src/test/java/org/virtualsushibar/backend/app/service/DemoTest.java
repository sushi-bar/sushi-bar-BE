package org.virtualsushibar.backend.app.service;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DemoTest {

    @Test
    public void main() throws Exception {

        var arr = new String[]{"5 4"};
        //Write code here
        String[] splitLineOne = arr[0].split(" ");
        int n = Integer.parseInt(splitLineOne[0]);
        int x = Integer.parseInt(splitLineOne[1]);

        var arr2 = "1 3 2 4 5";

        String[] sp = arr2.split(" ");
        int[] eng = new int[n];

        for(int j = 0 ; j< sp.length;j++){
            eng[j]=Integer.parseInt(sp[j]);
                    }

     //   Arrays.sort(eng);

        int sum = 0;
        int out = -1;
        for (int i = eng.length-1; i > 0; i--) {

            //1 2 3 4 5


            if(eng[i]%x ==1 || eng[i]/x==1 ){
                //System.out.println("continue for this eng"+eng[i]);
            }
            else{
                out=eng[i];
                break;
            }


        }


        System.out.println(out);

    }


  /*

    var arr = new String[]{"5 4"};
        //Write code here
        String[] splitLineOne = arr[0].split(" ");
        int n = Integer.parseInt(splitLineOne[0]);
        int x = Integer.parseInt(splitLineOne[1]);

        var arr2 = "1 3 2 4 5";

        String[] sp = arr2.split(" ");
        int sum =0;
    int out=-1;
       for(int i=0;i<sp.length;i++){

        sum=sum+ Integer.parseInt(sp[i]);

        if (sum == x ) {

            if(i!=sp.length-1){
                i=i+1;
            }

            out = Integer.parseInt(sp[i]);
            break;
        }
    }

         System.out.print(out);*/
}
