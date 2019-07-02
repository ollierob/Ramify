export function rethrowResponseError<T>(response: Response): Promise<T> {
    return response.text().then<T>(text => {
        if (!text) text = response.statusText;
        if (text.startsWith("<html") || text.startsWith("<!DOCTYPE html")) text = parseHtmlResponse(text);
        if (text.startsWith("ERROR:")) text = text.substr(6).trimLeft();
        throw new Error("Error fetching");
    });
}

function parseHtmlResponse(html: string): string {
    const body = document.createElement("html");
    body.innerHTML = html;
    const h1 = body.getElementsByTagName("h1");
    if (h1 && h1.length) return h1[0].innerText;
    const h2 = body.getElementsByTagName("h2");
    if (h2 && h2.length) return h2[0].innerText;
    return "Server error";
}

type Queryable = string | number | boolean;

export function queryParameters(query: {[key: string]: Queryable | Queryable[]}): string {
    if (!query) return "";
    const queryParameters: string[] = [];
    Object.keys(query).forEach(key => {
        const value = query[key];
        if (Array.isArray(value)) value.forEach(v => queryParameters.push(key + "=" + String(v)));
        else queryParameters.push(key + "=" + String(value));
    })
    if (!queryParameters.length) return "";
    return "?" + queryParameters.join("&");
}