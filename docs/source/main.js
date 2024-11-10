import { createElement } from "@modules/Elements.js"

export default () => {
    const title = createElement("h1", "Hello, World!", { id: "title" }, {
        backgroundColor: "lightblue",
    })
}
