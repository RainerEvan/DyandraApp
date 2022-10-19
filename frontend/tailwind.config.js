/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./src/**/*.{html,ts}",
    ],
    theme: {
        extend: {
            fontFamily: {
                inter: "'Inter', sans-serif",
                allerta : "'Allerta Stencil', sans-serif",
            },
            fontSize: {},
            fontWeight: {
                light: 300,
                regular: 400,
                medium: 500,
                semibold: 600,
                bold: 700
            },
            colors: {
                bca:'#0066AE',
            },
        },
    },
    plugins: [],
}