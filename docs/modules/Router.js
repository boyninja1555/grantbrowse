const routes = {}

function addRoute(path, callback) {
    routes[path] = callback
}

function useRoutes() {
    const route = `${location.pathname.split("grantbrowse/")[1]}/`
    const callback = routes[route]
    
    if (callback) {
        callback()
    } else {
        console.log("Route not found")
    }
}

export { addRoute, useRoutes }
