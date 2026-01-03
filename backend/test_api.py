import requests

def test_stock_search():
    url = "http://192.168.1.12:5000/stock/search"
    params = {
        "keyword":"贵州茅台"
    }
    print("请求中...", url, params)

    resp = requests.get(url, params=params, timeout=10)
    print("HTTP 状态码:", resp.status_code)

    if resp.status_code == 200:
        data = resp.json()
        print("返回数据:")
        for k, v in data.items():
            print(f"  {k}: {v}")
    else:
        print("错误返回:")
        print(resp.text)

def test_daily_price():
    url = "http://192.168.1.12:5000/price/daily"
    params = {
        "code": "600519",
        "date": "2024-01-05"
    }

    print("请求中...", url, params)

    resp = requests.get(url, params=params, timeout=10)

    print("HTTP 状态码:", resp.status_code)

    if resp.status_code == 200:
        data = resp.json()
        print("返回数据:")
        for k, v in data.items():
            print(f"  {k}: {v}")
    else:
        print("错误返回:")
        print(resp.text)

if __name__ == "__main__":
#     test_stock_search()
#     input(">")
    test_daily_price()
