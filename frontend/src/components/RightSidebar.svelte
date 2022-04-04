<script>
    import ToDo from "../components/ToDo.svelte";

    let date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();

    function getStringMonth() {
        if(month == 1) return "January";
        if(month == 2) return "February";
        if(month == 3) return "March";
        if(month == 4) return "April";
        if(month == 5) return "May";
        if(month == 6) return "June";
        if(month == 7) return "July";
        if(month == 8) return "August";
        if(month == 9) return "September";
        if(month == 10) return "October";
        if(month == 11) return "November";
        if(month == 12) return "December";
    }
</script>

<section class="right-sidebar">
    <div class="greeting">
        Welcome,&nbsp;<span class="username">Username</span>! 👋
    </div>

    <div class="calendar">
        <div class="calendar-title">{getStringMonth()} {day}, {year}</div>
    </div>

    <div class="sidebar-divider"></div>

    <div class="todo">
        <ToDo />
    </div>

</section>

<style>
    .right-sidebar {
        background-color: var(--background-primary);
        border-left: 1px solid var(--border-color);
        width: 22vw;
        height: 100vh;
        position: fixed;
        right: 0;
    }
    .greeting {
        padding: 2.5rem 1rem;
        padding-bottom: 0;
        display: flex;
        align-items: center;
        color: var(--text-color-primary);
    }
    .username {
        font-weight: bold;
    }
    .calendar {
        margin: .5rem 1rem;
    }
    .sidebar-divider {
        width: 90%;
        height: 1px;
        background-color: var(--border-color);
        margin-top: 1.2rem;
        margin: 1rem auto;
    }
    .todo {
        margin: .5rem 1rem;
    }
    @media screen and (max-width: 922px) {
        .right-sidebar {
            display: none;
        }
    }
</style>