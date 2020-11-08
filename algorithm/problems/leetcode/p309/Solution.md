# leetcode : Best Time to Buy and Sell Stock with Cooldown

## 문제 설명 [[링크]](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)

Say you have an array for which the *i*th element is the price of a given stock on day *i*.

Design an algorithm to find the maximum profit. You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times) with the following restrictions:

- You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
- After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)

**Example:**

```
Input: [1,2,3,0,2]
Output: 3 
Explanation: transactions = [buy, sell, cooldown, buy, sell]
```

​     

## 문제 풀이

[[이곳]](https://engkimbs.tistory.com/699)에 잘 설명되어 있다. 설명을 옮겨보자면 캐시는 다음의 3가지가 필요하다.

```
buy[]    : 거래가 buy로 끝나는 최대 이익 거래
sell[]   : 거래가 sell로 끝나는 최대 이익 거래
rest[]   : 거래가 rest로 끝나는 최대 이익 거래
```

여기서 각각의 경우를 보자.

​    

#### rest

```
rest[i] = max(sell[i-1], buy[i-1], rest[i-1])
```

여기서 buy는 price값을 빼기 때문에 `buy <= rest <= sell` 이 된다.
rest <= sell인데 rest[i-1]와 sell[i-1]를 비교하면 그대로 유지하는 것과 +price하는 것 중 후자가 더 크거나 같다는 것을 알 수 있다. 그래서 rest[i] = sell[i-1]이 된다.

```
rest[i] = sell[i-1]
```

​    

#### buy

```
buy[i] = max(buy[i-1], rest[i-1] - prices)
```

- 이전 구매했을 때의 캐시값을 가져 온다.
- 이전 유지한 상황에서 현재의 가격을 뺀다. (구입하기 때문에 가격만큼 빠진다). 

여기서 rest[i-1] = sell[i-2]이다. 따라서 아래와 같이 변경된다.

```
buy[i] = max(buy[i-1], sell[i-2] - price);
```

​    

#### sell

```
sell[i] = max(sell[i-1], buy[i-1] + price)
```

- sell[i-1] 값을 그대로 가져온다.
- 이전 구매한 상태에서 현재 가격에서 판매한다. 즉 price값 만큼 더해준다.

​    

여기까지 보았다면 rest는 사실상 쓸일이 없다는 것을 알게 된다. 즉 아래와 같이 정리된다.

```
buy[i] = max(buy[i-1], sell[i-2] - price);
sell[i] = max(sell[i-1], buy[i-1] + price)
```

다시 여기서 잘 보면 i번째 값을 구하기 위해서 필요한 것은 `price`, `i-1, i-2` 번째 buy, sell값이라는 것을 알 수 있다. 이것을 이용하면 배열이 아닌 몇개의 변수만 가지고 연산이 가능하다는 것을 알 수 있다.

​     

## 코드 구현

```js
var maxProfit = function(prices) {
  let sell_1 = 0;
  let buy_1 = Number.MIN_SAFE_INTEGER;
  let buy_2 = 0, sell_2 = 0;

  for(let i=0; i<prices.length; i++) {
    buy_2 = buy_1;
    buy_1 = Math.max(buy_1, sell_2 - prices[i]);

    sell_2 = sell_1;
    sell_1 = Math.max(sell_1, buy_1 + prices[i]);
  }
  return sell_1;
}
```



