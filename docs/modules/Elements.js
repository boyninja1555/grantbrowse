const root = document.getElementById("root")

function createElement(tagName, textContent, attributes, styles) {
    const element = document.createElement(tagName)
    element.textContent = textContent

    if (attributes) {
        Object.entries(attributes).forEach(([name, value]) => {
            element.setAttribute(name, value)
        })
    }

    if (styles) {
        Object.entries(styles).forEach(([name, value]) => {
            element.style[name] = value
        })
    }

    root.appendChild(element)
    return element
}

export { createElement }
