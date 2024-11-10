const root = document.getElementById("root")

function createElement(tagName, textContent, attributes) {
    const element = document.createElement(tagName)
    element.textContent = textContent

    if (attributes) {
        Object.entries(attributes).forEach(([name, value]) => {
            element.setAttribute(name, value)
        })
    }

    root.appendChild(element)
    return element
}

export { createElement }
