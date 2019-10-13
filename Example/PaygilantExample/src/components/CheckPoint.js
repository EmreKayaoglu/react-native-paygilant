var CheckPoint = Object.freeze({
    transactionType: "",
    timeStamp: 0,
    curType: "",
    userID: "",
    amount: 0,
    destinationId: "",
    paymentMethod: "",
})

export function getJsonFromCheckPoint(checkpoint) {
    var formBody = [];
    for (var property in checkpoint) {
        var encodedKey = encodeURIComponent(property);
        var encodedValue = encodeURIComponent(checkpoint[property]);
        formBody.push('"' + encodedKey + '":"' + encodedValue + '"');
    }
    var jsonStr = "{" + formBody.join(",") + "}"
    return jsonStr
}

export function getNewCheckPoint() {
    var newInstance = Object.assign({}, CheckPoint)
    return newInstance
}