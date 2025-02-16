function clearDisplay() {
    document.getElementById('display').value = '';
    document.getElementById('result').innerText = '';
}

function deleteLast() {
    const display = document.getElementById('display');
    display.value = display.value.slice(0, -1);
    calculateResult();
}

function appendToDisplay(value) {
    const display = document.getElementById('display');
    display.value += value;
    calculateResult();
}

function calculateResult() {
    const display = document.getElementById('display');
    const resultElement = document.getElementById('result');
    try {
        const result = eval(display.value);
        resultElement.innerText = result;

        // Add to history if equals button is pressed
        if (event && event.type === 'click' && event.target.innerText === '=') {
            addToHistory(display.value, result);
        }
    } catch (e) {
        resultElement.innerText = 'Error';
    }
}

function addToHistory(expression, result) {
    const historyList = document.getElementById('historyList');
    const historyItem = document.createElement('li');
    historyItem.innerText = `${expression} = ${result}`;
    historyList.appendChild(historyItem);
}