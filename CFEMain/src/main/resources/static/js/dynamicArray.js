class DynamicModifiableArray  {
    #newInputInnerHtml
    #wrapper
    #addButton
    #getOutputFunc
    #inputsCount = 0

    constructor(wrapper, addButton, newInputInnerHtml, getOutputFunc) {
        this.#wrapper = wrapper
        this.#addButton = addButton
        this.#newInputInnerHtml = newInputInnerHtml;
        this.#getOutputFunc = getOutputFunc;

        this.#init()
    }

    #init() {
        const _this = this;

        this.#addButton.addEventListener("click", function (e) {
            e.preventDefault();
            _this.#wrapper.appendChild(_this.#getNewInput());
        });

        this.#wrapper.addEventListener("click", function (e) { // delete field on remove button click
            if (e.target && e.target.matches("a.remove_field")) {
                e.preventDefault();
                e.target.parentNode.parentNode.removeChild(e.target.parentNode);
                _this.#inputsCount--; // decrement input field count
            }
        });
    }

    #getNewInput() {
        const htmlDivElement = document.createElement("div");
        htmlDivElement.className = 'input_field'
        htmlDivElement.innerHTML += this.#newInputInnerHtml

        this.#inputsCount++;
        return htmlDivElement;
    }

    getOutput() {
        const inputFields = this.#wrapper.children;

        const values = [];
        for (let i = 0; i < inputFields.length; i++) {
            this.#getOutputFunc(inputFields, values, i)
        }
        return values;
    }
}

class Variable {
    name;
    type;
    value;

    constructor(name, type, value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }
}

class Exercise {
    id;
    title;
    actualVariablesIn;
    expectedOut;

    constructor(id, title, actualVariablesIn, expectedOut) {
        this.id = id
        this.title = title
        this.actualVariablesIn = actualVariablesIn
        this.expectedOut = expectedOut
    }
}