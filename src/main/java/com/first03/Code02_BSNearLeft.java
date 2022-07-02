package com.first03;

/**
 * @Description: 二分查找 >=num的最左的位置
 * @author: li
 * @create: 2022-05-07 20:32
 */
public class Code02_BSNearLeft {

    public static int mostLeftNoLessNumIndex(int[] arr,int num){

        if (arr==null||arr.length==0){
            return -1;
        }

        int L=0;
        int R=arr.length-1;
        int ans=-1;

        //更新t
        while (L<=R){
          int mid=L+(R-L)>>1;

          if (arr[mid]>=num){
              //记录ans，并继续更新R
              ans=mid;
              R=mid-1;
          }else {
              L=mid+1;
          }
        }

        return ans;
    }
}
