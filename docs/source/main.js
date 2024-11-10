import { addRoute, setErrorCallback } from "@modules/Router.js"
import { createElement } from "@modules/Elements.js"

import errorPage from "./pages/Error.js"

export default () => {
    addRoute("/", () => {
        const title = createElement("h1", "Hello, World!", { id: "title" }, {
            backgroundColor: "lightblue",
        })
    })
    setErrorCallback(errorPage)
}
