import { addRoute, useRoutes } from "@modules/Router.js"
import { createElement } from "@modules/Elements.js"

export default () => {
    const title = createElement("h1", "Hello, World!", { id: "title" }, {
        backgroundColor: "lightblue",
    })

    addRoute("/", () => {
        alert("Welcome home!")
    })
    useRoutes()
}
