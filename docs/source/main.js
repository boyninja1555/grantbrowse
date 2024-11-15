import { addRoute, setErrorCallback, useRoutes } from "@modules/Router.js"
import { createElement } from "@modules/Elements.js"

import errorPage from "./pages/Error.js"

export default () => {
    setErrorCallback(errorPage)

    addRoute("/", () => {
        const title = createElement("h1", "Hello, World!", { id: "title" }, {
            backgroundColor: "lightblue",
        })
    })

    addEventListener("hashchange", useRoutes)
    addEventListener("load", useRoutes)
}
