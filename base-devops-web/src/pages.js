const pages = [

    // Dashboards

    {
        output: './index.html',
        content: {
            title: 'Dashboard',
            description: '仪表盘',
            heading_icon: 'pe-7s-car icon-gradient bg-mean-fruit'
        },
        template: './src/Pages/dashboards/dashboard.hbs'
    },

    // PROJECTS
    {
        output: './projects.html',
        content: {
            title: '项目管理',
            description: '项目列表',
            heading_icon: 'pe-7s-menu icon-gradient bg-mean-fruit'
        },
        template: './src/Pages/projects/project.hbs'
    },

    // CREATE PROJECT
    {
        output: './create-project.html',
        content: {
            title: '项目管理',
            description: '新建项目',
            heading_icon: 'pe-7s-menu icon-gradient bg-mean-fruit'
        },
        template: './src/Pages/projects/project-create.hbs'
    },

    // PROJECT DETAILS
    {
        output: './project-detail.html',
        content: {
            title: '项目管理',
            description: '项目详情',
            heading_icon: 'pe-7s-menu icon-gradient bg-mean-fruit'
        },
        template: './src/Pages/projects/project-detail.hbs'
    },

    // PROJECT EDIT
    {
        output: './project-detail.html',
        content: {
            title: '项目管理',
            description: '项目编辑',
            heading_icon: 'pe-7s-menu icon-gradient bg-mean-fruit'
        },
        template: './src/Pages/projects/project-edit.hbs'
    },

    // UI Elements
    {
        output: './elements-buttons-standard.html',
        content: {
            title: 'Standard Buttons',
            description: 'Wide selection of buttons that feature different styles for backgrounds, borders and hover options!',
            heading_icon: 'pe-7s-plane icon-gradient bg-tempting-azure'
        },
        template: './src/DemoPages/elements/buttons/standard.hbs'
    },

    {
        output: './elements-badges-labels.html',
        content: {
            title: 'Badges & Labels',
            description: 'Badges and labels are used to offer extra small pieces of info for your content.',
            heading_icon: 'pe-7s-voicemail icon-gradient bg-arielle-smile'
        },
        template: './src/DemoPages/elements/badges-labels.hbs'
    },
    {
        output: './elements-dropdowns.html',
        content: {
            title: 'Dropdowns',
            description: 'Multiple styles, actions and effects are available for the Archited Framework dropdown buttons.',
            heading_icon: 'pe-7s-umbrella icon-gradient bg-sunny-morning'
        },
        template: './src/DemoPages/elements/dropdowns.hbs'
    },
    {
        output: './elements-icons.html',
        content: {
            title: 'Icons',
            description: 'Wide icons selection including from flag icons to FontAwesome and other icons libraries.',
            heading_icon: 'pe-7s-phone icon-gradient bg-night-fade'
        },
        template: './src/DemoPages/elements/icons.hbs'
    },
    {
        output: './elements-cards.html',
        content: {
            title: 'Cards',
            description: 'Wide selection of cards with multiple styles, borders, actions and hover effects.',
            heading_icon: 'pe-7s-stopwatch icon-gradient bg-amy-crisp'
        },
        template: './src/DemoPages/elements/cards.hbs'
    },

    {
        output: './elements-list-group.html',
        content: {
            title: 'List Groups',
            description: 'These can be used with other components and elements to create stunning and unique new elements for your UIs.',
            heading_icon: 'pe-7s-paint icon-gradient bg-sunny-morning'
        },
        template: './src/DemoPages/elements/list-group.hbs'
    },
    {
        output: './elements-navigation.html',
        content: {
            title: 'Navigation Menus',
            description: 'Navigation menus are one of the basic building blocks for any web or mobile app.',
            heading_icon: 'pe-7s-photo-gallery icon-gradient bg-mean-fruit'
        },
        template: './src/DemoPages/elements/navigation.hbs'
    },

    {
        output: './elements-utilities.html',
        content: {
            title: 'Utilities',
            description: 'These are helpers that speed up the dev time for various components and effects.',
            heading_icon: 'pe-7s-wristwatch icon-gradient bg-deep-blue'
        },
        template: './src/DemoPages/elements/utilities.hbs'
    }
];

module.exports = pages;
