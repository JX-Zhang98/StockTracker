import random
import tushare as ts
from flask import Flask, request, jsonify

# ===== 1. 配置 tushare =====
with open("./tokens.secret", 'r') as f:
    tokens = [i.strip() for i in f.readlines() if i.strip()]

pro = ts.pro_api()

app = Flask(__name__)
# ===== 2. 股票日线接口 =====
@app.route("/price/daily")
def daily_price():
    ts.set_token(random.sample(tokens, 1))
    code = request.args.get("code")
    date = request.args.get("date")

    if not code or not date:
        return jsonify({"error": "missing code or date"}), 400

    trade_date = date.replace("-", "")

    ts_code = (
        f"{code}.SH" if code.startswith("6") else f"{code}.SZ"
    )

    df = pro.daily(
        ts_code=ts_code,
        start_date=trade_date,
        end_date=trade_date
    )

    if df.empty:
        return jsonify({"error": "no data"}), 404

    row = df.iloc[0]

    return jsonify({
        "code": code,
        "date": date,
        "open": float(row["open"]),
        "close": float(row["close"]),
        "high": float(row["high"]),
        "low": float(row["low"])
    })

# ===== 股票信息查询接口 =====
@app.route("/stock/search")
def search_stock():
    ts.set_token(random.sample(tokens, 1))
    keyword = request.args.get("keyword")
    if not keyword:
        return jsonify({"error": "missing keyword"}), 400

    # 转成 Tushare 格式：获取股票列表
    try:
        df = pro.stock_basic(exchange='', list_status='L', fields='ts_code,symbol,name')
    except Exception as e:
        print(type(e))
        return jsonify({"error": str(e)}), 500

    # 模糊匹配
    df_filtered = df[df['name'].str.contains(keyword) | df['symbol'].str.contains(keyword)]

    if df_filtered.empty:
        return jsonify({"error": "no stock found"}), 404

    # 返回第一条匹配
    row = df_filtered.iloc[0]
    ts_code = row['symbol']  # 例如 '600519'
    name = row['name']

    return jsonify({
        "code": ts_code,
        "name": name
    })


# ===== 3. 启动服务 =====
if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=False)
